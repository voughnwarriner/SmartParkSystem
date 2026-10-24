package com.smartpark.dto;

import com.smartpark.entity.ParkingSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CheckoutResponse(
        String lotId,
        String licensePlate,
        String vehicleType,
        LocalDateTime checkInTime,
        LocalDateTime checkOutTime,
        long billableHours,
        BigDecimal parkingCost,
        String status
) {
    public static CheckoutResponse from(ParkingSession session) {
        return new CheckoutResponse(
                session.getParkingLot().getLotId(),
                session.getVehicle().getLicensePlate(),
                session.getVehicle().getType().name(),
                session.getCheckInTime(),
                session.getCheckOutTime(),
                session.getBillableHours(),
                session.getParkingCost(),
                session.getStatus().name());
    }
}
