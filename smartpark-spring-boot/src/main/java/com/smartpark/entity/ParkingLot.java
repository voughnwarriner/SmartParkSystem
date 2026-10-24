package com.smartpark.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "parking_lots", uniqueConstraints = {
        @UniqueConstraint(name = "uk_parking_lot_business_id", columnNames = "lot_id")
})
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lot_id", nullable = false, length = 50, unique = true)
    private String lotId;

    @Column(nullable = false, length = 200)
    private String location;

    @Column(nullable = false)
    private int capacity;

    @Column(name = "occupied_spaces", nullable = false)
    private int occupiedSpaces = 0;

    protected ParkingLot() {
    }

    public ParkingLot(String lotId, String location, int capacity) {
        this.lotId = lotId;
        this.location = location;
        this.capacity = capacity;
        this.occupiedSpaces = 0;
    }

    public Long getId() { return id; }
    public String getLotId() { return lotId; }
    public String getLocation() { return location; }
    public int getCapacity() { return capacity; }
    public int getOccupiedSpaces() { return occupiedSpaces; }
    public int getAvailableSpaces() { return capacity - occupiedSpaces; }

    public void occupyOneSpace() {
        if (occupiedSpaces >= capacity) {
            throw new IllegalStateException("Parking lot is full");
        }
        occupiedSpaces++;
    }

    public void releaseOneSpace() {
        if (occupiedSpaces > 0) {
            occupiedSpaces--;
        }
    }
}
