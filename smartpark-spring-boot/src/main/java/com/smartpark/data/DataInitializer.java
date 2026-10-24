package com.smartpark.data;

import com.smartpark.entity.ParkingLot;
import com.smartpark.entity.Vehicle;
import com.smartpark.entity.VehicleType;
import com.smartpark.repository.ParkingLotRepository;
import com.smartpark.repository.VehicleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner preloadSampleData(
            ParkingLotRepository lotRepository,
            VehicleRepository vehicleRepository) {
        return args -> {
            if (lotRepository.count() == 0) {
                lotRepository.save(new ParkingLot("LOT-001", "Makati CBD", 20));
                lotRepository.save(new ParkingLot("LOT-002", "BGC, Taguig", 10));
            }
            if (vehicleRepository.count() == 0) {
                vehicleRepository.save(new Vehicle("ABC-1234", VehicleType.CAR, "Juan Dela Cruz"));
                vehicleRepository.save(new Vehicle("MOTO-001", VehicleType.MOTORCYCLE, "Maria Santos"));
                vehicleRepository.save(new Vehicle("TRK-2025", VehicleType.TRUCK, "Pedro Reyes"));
            }
        };
    }
}
