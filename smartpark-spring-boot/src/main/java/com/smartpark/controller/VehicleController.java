package com.smartpark.controller;

import com.smartpark.dto.CreateVehicleRequest;
import com.smartpark.dto.VehicleResponse;
import com.smartpark.service.ParkingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    private final ParkingService parkingService;

    public VehicleController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehicleResponse register(@Valid @RequestBody CreateVehicleRequest request) {
        return parkingService.registerVehicle(request);
    }

    @GetMapping("/{licensePlate}")
    public VehicleResponse getOne(@PathVariable String licensePlate) {
        return parkingService.getVehicle(licensePlate);
    }

    @GetMapping
    public List<VehicleResponse> getAll() {
        return parkingService.getAllVehicles();
    }
}
