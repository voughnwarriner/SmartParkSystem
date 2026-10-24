package com.smartpark.dto;

public record OccupancyResponse(
        String lotId,
        int capacity,
        int occupiedSpaces,
        int availableSpaces
) {
}
