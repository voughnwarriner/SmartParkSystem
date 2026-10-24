package com.smartpark.dto;

import com.smartpark.entity.Vehicle;

public record VehicleResponse(
        String licensePlate,
        String type,
        String ownerName
) {
    public static VehicleResponse from(Vehicle vehicle) {
        return new VehicleResponse(
                vehicle.getLicensePlate(), vehicle.getType().name(), vehicle.getOwnerName());
    }
}
