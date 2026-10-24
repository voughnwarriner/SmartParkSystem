package com.smartpark.controller;

import com.smartpark.dto.*;
import com.smartpark.service.ParkingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lots")
public class ParkingLotController {
    private final ParkingService parkingService;

    public ParkingLotController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingLotResponse create(@Valid @RequestBody CreateParkingLotRequest request) {
        return parkingService.createParkingLot(request);
    }

    @GetMapping
    public List<ParkingLotResponse> getAll() {
        return parkingService.getAllParkingLots();
    }

    @GetMapping("/{lotId}")
    public ParkingLotResponse getOne(@PathVariable String lotId) {
        return parkingService.getParkingLot(lotId);
    }

    @GetMapping("/{lotId}/occupancy")
    public OccupancyResponse occupancy(@PathVariable String lotId) {
        return parkingService.getOccupancy(lotId);
    }

    @GetMapping("/{lotId}/vehicles")
    public List<ParkingSessionResponse> parkedVehicles(@PathVariable String lotId) {
        return parkingService.getParkedVehicles(lotId);
    }

    @PostMapping("/{lotId}/check-in")
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingSessionResponse checkIn(
            @PathVariable String lotId, @Valid @RequestBody CheckInRequest request) {
        return parkingService.checkIn(lotId, request);
    }

    @PostMapping("/{lotId}/check-out")
    public CheckoutResponse checkOut(
            @PathVariable String lotId, @Valid @RequestBody CheckOutRequest request) {
        return parkingService.checkOut(lotId, request);
    }
}
