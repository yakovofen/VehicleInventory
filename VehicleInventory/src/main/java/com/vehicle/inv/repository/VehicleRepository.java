package com.vehicle.inv.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vehicle.inv.model.Vehicle;
import com.vehicle.inv.model.VehicleType;

@Repository
public interface VehicleRepository extends CrudRepository<Vehicle, Long> {

	Optional<Vehicle> findByVin(String vin);
	
	
	Optional<Vehicle> findByPlateNumber(String plateNumber);
	
	
	List<Vehicle> findByVehicleType(VehicleType type);
	
	
	Optional<Vehicle> findByPlateNumberOrVin(String plateNumber, String vin);

}
