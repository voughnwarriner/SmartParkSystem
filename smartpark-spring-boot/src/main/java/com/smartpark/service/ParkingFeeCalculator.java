package com.smartpark.service;

import com.smartpark.entity.VehicleType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ParkingFeeCalculator {
    private static final long INCLUDED_HOURS = 3;

    public long calculateBillableHours(LocalDateTime checkIn, LocalDateTime checkOut) {
        if (checkIn == null || checkOut == null || checkOut.isBefore(checkIn)) {
            throw new IllegalArgumentException("Invalid parking timestamps");
        }

        long elapsedSeconds = Duration.between(checkIn, checkOut).getSeconds();
        if (elapsedSeconds <= 0) {
            return 0;
        }
        return (elapsedSeconds + 3599) / 3600;
    }

    public BigDecimal calculateFee(
            VehicleType vehicleType, LocalDateTime checkIn, LocalDateTime checkOut) {
        if (vehicleType == null) {
            throw new IllegalArgumentException("Vehicle type is required");
        }

        long billableHours = calculateBillableHours(checkIn, checkOut);
        long additionalHours = Math.max(0, billableHours - INCLUDED_HOURS);

        BigDecimal baseFee;
        BigDecimal additionalHourlyFee;
        switch (vehicleType) {
            case MOTORCYCLE -> {
                baseFee = new BigDecimal("30.00");
                additionalHourlyFee = new BigDecimal("10.00");
            }
            case CAR -> {
                baseFee = new BigDecimal("60.00");
                additionalHourlyFee = new BigDecimal("20.00");
            }
            case TRUCK -> {
                baseFee = new BigDecimal("120.00");
                additionalHourlyFee = new BigDecimal("40.00");
            }
            default -> throw new IllegalArgumentException("Unsupported vehicle type");
        }

        // The first three hours (or any shorter stay) cost the base fee.
        return baseFee.add(additionalHourlyFee.multiply(BigDecimal.valueOf(additionalHours)));
    }
}
