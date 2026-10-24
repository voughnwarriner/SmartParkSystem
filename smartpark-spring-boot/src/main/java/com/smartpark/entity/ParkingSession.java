package com.smartpark.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "parking_sessions", indexes = {
        @Index(name = "idx_session_status_checkin", columnList = "status, check_in_time")
})
public class ParkingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parking_lot_id", nullable = false)
    private ParkingLot parkingLot;

    @Column(name = "check_in_time", nullable = false)
    private LocalDateTime checkInTime;

    @Column(name = "check_out_time")
    private LocalDateTime checkOutTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ParkingSessionStatus status;

    @Column(name = "billable_hours")
    private Long billableHours;

    @Column(name = "parking_cost", precision = 12, scale = 2)
    private BigDecimal parkingCost;

    protected ParkingSession() {
    }

    public ParkingSession(Vehicle vehicle, ParkingLot parkingLot, LocalDateTime checkInTime) {
        this.vehicle = vehicle;
        this.parkingLot = parkingLot;
        this.checkInTime = checkInTime;
        this.status = ParkingSessionStatus.ACTIVE;
    }

    public Long getId() { return id; }
    public Vehicle getVehicle() { return vehicle; }
    public ParkingLot getParkingLot() { return parkingLot; }
    public LocalDateTime getCheckInTime() { return checkInTime; }
    public LocalDateTime getCheckOutTime() { return checkOutTime; }
    public ParkingSessionStatus getStatus() { return status; }
    public Long getBillableHours() { return billableHours; }
    public BigDecimal getParkingCost() { return parkingCost; }

    public boolean isActive() {
        return status == ParkingSessionStatus.ACTIVE;
    }

    public void finish(LocalDateTime endTime, ParkingSessionStatus finalStatus,
                       long billableHours, BigDecimal parkingCost) {
        if (!isActive()) {
            throw new IllegalStateException("Parking session is not active");
        }
        this.checkOutTime = endTime;
        this.status = finalStatus;
        this.billableHours = billableHours;
        this.parkingCost = parkingCost;
    }
}
