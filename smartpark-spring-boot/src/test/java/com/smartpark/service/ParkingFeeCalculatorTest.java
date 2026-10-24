package com.smartpark.service;

import com.smartpark.entity.VehicleType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParkingFeeCalculatorTest {
    private final ParkingFeeCalculator calculator = new ParkingFeeCalculator();
    private final LocalDateTime start = LocalDateTime.of(2026, 1, 1, 8, 0);

    @Test
    void motorcycleCostsBaseFeeForThreeHours() {
        assertEquals(new BigDecimal("30.00"),
                calculator.calculateFee(VehicleType.MOTORCYCLE, start, start.plusHours(3)));
    }

    @Test
    void carCostsBaseFeeForThreeHoursAndOneAdditionalHourAfterward() {
        assertEquals(new BigDecimal("80.00"),
                calculator.calculateFee(VehicleType.CAR, start, start.plusHours(3).plusSeconds(1)));
    }

    @Test
    void truckCostsBaseFeePlusTwoAdditionalHours() {
        assertEquals(new BigDecimal("200.00"),
                calculator.calculateFee(VehicleType.TRUCK, start, start.plusHours(5)));
    }

    @Test
    void partialFirstHourStillUsesBaseFee() {
        assertEquals(new BigDecimal("60.00"),
                calculator.calculateFee(VehicleType.CAR, start, start.plusMinutes(1)));
    }

    @Test
    void roundsPartialHourUp() {
        assertEquals(4, calculator.calculateBillableHours(start, start.plusHours(3).plusSeconds(1)));
    }

    @Test
    void rejectsCheckoutBeforeCheckin() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.calculateFee(VehicleType.CAR, start, start.minusMinutes(1)));
    }
}
