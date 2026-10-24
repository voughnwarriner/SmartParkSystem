package com.smartpark.repository;

import com.smartpark.entity.ParkingSession;
import com.smartpark.entity.ParkingSessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ParkingSessionRepository extends JpaRepository<ParkingSession, Long> {
    boolean existsByVehicleIdAndStatus(Long vehicleId, ParkingSessionStatus status);

    Optional<ParkingSession> findByParkingLotLotIdAndVehicleLicensePlateAndStatus(
            String lotId, String licensePlate, ParkingSessionStatus status);

    List<ParkingSession> findByParkingLotLotIdAndStatusOrderByCheckInTimeAsc(
            String lotId, ParkingSessionStatus status);

    List<ParkingSession> findByStatusAndCheckInTimeBefore(
            ParkingSessionStatus status, LocalDateTime cutoff);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from ParkingSession s where s.id = :id")
    Optional<ParkingSession> findByIdForUpdate(@Param("id") Long id);
}
