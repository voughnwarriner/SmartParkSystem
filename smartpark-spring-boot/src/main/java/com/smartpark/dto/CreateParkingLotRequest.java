package com.smartpark.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateParkingLotRequest(
        @NotBlank @Size(max = 50) String lotId,
        @NotBlank @Size(max = 200) String location,
        @Min(1) int capacity
) {
}
