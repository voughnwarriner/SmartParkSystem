package com.smartpark.service;

import com.smartpark.dto.*;
import com.smartpark.entity.*;
import com.smartpark.exception.BadRequestException;
import com.smartpark.exception.ConflictException;
import com.smartpark.exception.NotFoundException;
import com.smartpark.repository.ParkingLotRepository;
import com.smartpark.repository.ParkingSessionRepository;
import com.smartpark.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ParkingService {
    private final ParkingLotRepository lotRepository;
    private final VehicleRepository vehicleRepository;
    private final ParkingSessionRepository sessionRepository;
    private final ParkingFeeCalculator feeCalculator;

    public ParkingService(
            ParkingLotRepository lotRepository,
            VehicleRepository vehicleRepository,
            ParkingSessionRepository sessionRepository,
            ParkingFeeCalculator feeCalculator) {
        this.lotRepository = lotRepository;
        this.vehicleRepository = vehicleRepository;
        this.sessionRepository = sessionRepository;
        this.feeCalculator = feeCalculator;
    }

    @Transactional
    public ParkingLotResponse createParkingLot(CreateParkingLotRequest request) {
        String lotId = request.lotId().trim();
        if (lotRepository.existsByLotId(lotId)) {
            throw new ConflictException("Parking lot ID already exists: " + lotId);
        }
        if (request.capacity() < 1) {
            throw new BadRequestException("Capacity must be at least 1");
        }
        ParkingLot lot = lotRepository.save(
                new ParkingLot(lotId, request.location().trim(), request.capacity()));
        return ParkingLotResponse.from(lot);
    }

    @Transactional(readOnly = true)
    public List<ParkingLotResponse> getAllParkingLots() {
        return lotRepository.findAll().stream().map(ParkingLotResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public ParkingLotResponse getParkingLot(String lotId) {
        return ParkingLotResponse.from(findLot(lotId));
    }

    @Transactional
    public VehicleResponse registerVehicle(CreateVehicleRequest request) {
        String plate = request.licensePlate().trim().toUpperCase();
        if (vehicleRepository.existsByLicensePlate(plate)) {
            throw new ConflictException("License plate already registered: " + plate);
        }
        Vehicle vehicle = vehicleRepository.save(
                new Vehicle(plate, request.type(), request.ownerName().trim()));
        return VehicleResponse.from(vehicle);
    }

    @Transactional(readOnly = true)
    public VehicleResponse getVehicle(String licensePlate) {
        return VehicleResponse.from(findVehicle(licensePlate));
    }

    @Transactional(readOnly = true)
    public List<VehicleResponse> getAllVehicles() {
        return vehicleRepository.findAll().stream().map(VehicleResponse::from).toList();
    }

    @Transactional
    public ParkingSessionResponse checkIn(String lotId, CheckInRequest request) {
        // Lock the vehicle so simultaneous requests cannot park it in two lots.
        Vehicle vehicle = vehicleRepository.findByLicensePlateForUpdate(normalizePlate(request.licensePlate()))
                .orElseThrow(() -> new NotFoundException(
                        "Vehicle not found: " + normalizePlate(request.licensePlate())));

        ParkingLot lot = lotRepository.findByLotIdForUpdate(lotId)
                .orElseThrow(() -> new NotFoundException("Parking lot not found: " + lotId));

        if (sessionRepository.existsByVehicleIdAndStatus(
                vehicle.getId(), ParkingSessionStatus.ACTIVE)) {
            throw new ConflictException("Vehicle is already parked in a lot");
        }
        if (lot.getAvailableSpaces() <= 0) {
            throw new ConflictException("Parking lot is full");
        }

        LocalDateTime now = LocalDateTime.now();
        ParkingSession session = sessionRepository.save(new ParkingSession(vehicle, lot, now));
        lot.occupyOneSpace();
        return ParkingSessionResponse.from(session);
    }

    @Transactional
    public CheckoutResponse checkOut(String lotId, CheckOutRequest request) {
        ParkingLot lot = lotRepository.findByLotIdForUpdate(lotId)
                .orElseThrow(() -> new NotFoundException("Parking lot not found: " + lotId));
        String plate = normalizePlate(request.licensePlate());

        ParkingSession session = sessionRepository
                .findByParkingLotLotIdAndVehicleLicensePlateAndStatus(
                        lotId, plate, ParkingSessionStatus.ACTIVE)
                .orElseThrow(() -> new NotFoundException(
                        "No active parking session found for vehicle " + plate + " in lot " + lotId));

        LocalDateTime now = LocalDateTime.now();
        long billableHours = feeCalculator.calculateBillableHours(session.getCheckInTime(), now);
        BigDecimal fee = feeCalculator.calculateFee(
                session.getVehicle().getType(), session.getCheckInTime(), now);
        session.finish(now, ParkingSessionStatus.COMPLETED, billableHours, fee);
        lot.releaseOneSpace();
        return CheckoutResponse.from(session);
    }

    @Transactional(readOnly = true)
    public OccupancyResponse getOccupancy(String lotId) {
        ParkingLot lot = findLot(lotId);
        return new OccupancyResponse(lot.getLotId(), lot.getCapacity(),
                lot.getOccupiedSpaces(), lot.getAvailableSpaces());
    }

    @Transactional(readOnly = true)
    public List<ParkingSessionResponse> getParkedVehicles(String lotId) {
        findLot(lotId);
        return sessionRepository.findByParkingLotLotIdAndStatusOrderByCheckInTimeAsc(
                        lotId, ParkingSessionStatus.ACTIVE)
                .stream().map(ParkingSessionResponse::from).toList();
    }

    @Transactional
    public int autoRemoveOverdueVehicles() {
        LocalDateTime cutoff = LocalDateTime.now().minusMinutes(15);
        List<ParkingSession> overdue = sessionRepository.findByStatusAndCheckInTimeBefore(
                ParkingSessionStatus.ACTIVE, cutoff);
        int removed = 0;

        for (ParkingSession candidate : overdue) {
            // Lock the lot before the session, matching checkout's lock order.
            String lotId = candidate.getParkingLot().getLotId();
            ParkingLot lot = lotRepository.findByLotIdForUpdate(lotId).orElse(null);
            if (lot == null) {
                continue;
            }

            // Re-lock and re-read the session so a concurrent checkout cannot be processed twice.
            ParkingSession session = sessionRepository.findByIdForUpdate(candidate.getId()).orElse(null);
            if (session == null || !session.isActive()
                    || !session.getCheckInTime().isBefore(cutoff)) {
                continue;
            }

            LocalDateTime now = LocalDateTime.now();
            long billableHours = feeCalculator.calculateBillableHours(
                    session.getCheckInTime(), now);
            BigDecimal fee = feeCalculator.calculateFee(
                    session.getVehicle().getType(), session.getCheckInTime(), now);
            session.finish(now, ParkingSessionStatus.AUTO_REMOVED, billableHours, fee);
            lot.releaseOneSpace();
            removed++;
        }
        return removed;
    }

    private ParkingLot findLot(String lotId) {
        return lotRepository.findByLotId(lotId)
                .orElseThrow(() -> new NotFoundException("Parking lot not found: " + lotId));
    }

    private Vehicle findVehicle(String licensePlate) {
        String plate = normalizePlate(licensePlate);
        return vehicleRepository.findByLicensePlate(plate)
                .orElseThrow(() -> new NotFoundException("Vehicle not found: " + plate));
    }

    private String normalizePlate(String licensePlate) {
        return licensePlate.trim().toUpperCase();
    }
}
