package com.smartpark.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CheckOutRequest(
        @NotBlank @Size(max = 30)
        @Pattern(regexp = "^[A-Za-z0-9-]+$", message = "License plate may contain only letters, numbers, and dashes")
        String licensePlate
) {
}
