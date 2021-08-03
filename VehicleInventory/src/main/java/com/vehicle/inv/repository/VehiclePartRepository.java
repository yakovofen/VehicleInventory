package com.vehicle.inv.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.vehicle.inv.model.VehiclePart;

@Repository
public interface VehiclePartRepository extends CrudRepository<VehiclePart, Long> {
	
	
	Optional<VehiclePart> findByName(String name);

}
