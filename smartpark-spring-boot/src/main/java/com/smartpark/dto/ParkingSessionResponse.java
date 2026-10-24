package com.smartpark.dto;

import com.smartpark.entity.ParkingSession;
import java.time.LocalDateTime;

public record ParkingSessionResponse(
        String lotId,
        String licensePlate,
        String vehicleType,
        LocalDateTime checkInTime,
        LocalDateTime checkOutTime,
        String status
) {
    public static ParkingSessionResponse from(ParkingSession session) {
        return new ParkingSessionResponse(
                session.getParkingLot().getLotId(),
                session.getVehicle().getLicensePlate(),
                session.getVehicle().getType().name(),
                session.getCheckInTime(),
                session.getCheckOutTime(),
                session.getStatus().name());
    }
}
