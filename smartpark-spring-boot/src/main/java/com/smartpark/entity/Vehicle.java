package com.smartpark.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "vehicles", uniqueConstraints = {
        @UniqueConstraint(name = "uk_vehicle_license_plate", columnNames = "license_plate")
})
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "license_plate", nullable = false, length = 30, unique = true)
    private String licensePlate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private VehicleType type;

    @Column(name = "owner_name", nullable = false, length = 120)
    private String ownerName;

    protected Vehicle() {
    }

    public Vehicle(String licensePlate, VehicleType type, String ownerName) {
        this.licensePlate = licensePlate;
        this.type = type;
        this.ownerName = ownerName;
    }

    public Long getId() { return id; }
    public String getLicensePlate() { return licensePlate; }
    public VehicleType getType() { return type; }
    public String getOwnerName() { return ownerName; }
}
