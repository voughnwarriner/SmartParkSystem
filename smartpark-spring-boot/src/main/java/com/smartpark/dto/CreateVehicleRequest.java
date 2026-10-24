package com.smartpark.dto;

import com.smartpark.entity.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateVehicleRequest(
        @NotBlank
        @Size(max = 30)
        @Pattern(regexp = "^[A-Za-z0-9-]+$", message = "License plate may contain only letters, numbers, and dashes")
        String licensePlate,

        @NotNull VehicleType type,

        @NotBlank
        @Size(max = 120)
        @Pattern(regexp = "^[\\p{L} ]+$", message = "Owner name may contain only letters and spaces")
        String ownerName
) {
}
