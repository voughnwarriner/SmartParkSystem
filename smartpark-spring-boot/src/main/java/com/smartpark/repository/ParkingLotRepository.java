package com.smartpark.repository;

import com.smartpark.entity.ParkingLot;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {
    Optional<ParkingLot> findByLotId(String lotId);
    boolean existsByLotId(String lotId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from ParkingLot p where p.lotId = :lotId")
    Optional<ParkingLot> findByLotIdForUpdate(@Param("lotId") String lotId);
}
