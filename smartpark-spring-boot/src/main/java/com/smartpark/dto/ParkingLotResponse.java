package com.smartpark.dto;

import com.smartpark.entity.ParkingLot;

public record ParkingLotResponse(
        String lotId,
        String location,
        int capacity,
        int occupiedSpaces,
        int availableSpaces
) {
    public static ParkingLotResponse from(ParkingLot lot) {
        return new ParkingLotResponse(
                lot.getLotId(), lot.getLocation(), lot.getCapacity(),
                lot.getOccupiedSpaces(), lot.getAvailableSpaces());
    }
}
