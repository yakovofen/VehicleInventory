package com.vehicle.inv.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vehicle.inv.model.VehicleType;

@Repository
public interface VehicleTypeRepository extends CrudRepository<VehicleType, Long> {

	Optional<VehicleType> findByTypeName(String name);

}
