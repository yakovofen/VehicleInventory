package com.vehicle.inv.service;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vehicle.inv.model.VehicleType;
import com.vehicle.inv.repository.VehicleTypeRepository;
import com.vehicle.inv.utils.request.VehicleTypeRequest;

@Service 
public class VehicleTypeService {
	
	
	@Autowired
	VehicleTypeRepository vehicleTypeRepository;
	
	
	/**
	 * @param id
	 * @return
	 */
	public VehicleType getVehicleTypeById(Long id) {
		
		VehicleType type = null;
		Optional<VehicleType> optional = vehicleTypeRepository.findById(id);
		
		if(optional.isPresent()) {
			 type = optional.get();
		}
		return type;
	}
	
	
	/**
	 * @param name
	 * @return
	 */
	public VehicleType getVehicleTypeByName(String name) {
		
		VehicleType type = null;
		Optional<VehicleType> optional = vehicleTypeRepository.findByTypeName(name);
		
		if(optional.isPresent()) {
			 type = optional.get();
		}
		return type;
	}
	
	
	
	/**
	 * @param id
	 * @return
	 */
	public List<VehicleType> getAllVehicleTypes() {
		
		List<VehicleType> typeList = new ArrayList<VehicleType>();
		typeList = (List<VehicleType>) vehicleTypeRepository.findAll();
		return typeList;
	}
	
	
	/**
	 * @param typeName
	 * @return
	 */
	public VehicleType createVehicleType(String typeName) {

		VehicleType response = null;
		if (typeName != null) {
			VehicleType type = getVehicleTypeByName(typeName);
			if (type == null) {
				type = new VehicleType();
				type.setTypeName(typeName);
				response = vehicleTypeRepository.save(type);
			}				
		}
		return response;
	} 
	
	
	
	/**
	 * @param request
	 * @return
	 * @throws MalformedURLException
	 */
	public VehicleType updateVehicleType(VehicleTypeRequest request) {

		VehicleType response = null;
		VehicleType type = new VehicleType();

		if (request != null) {
			if (request.getId() != null && request.getTypeName() != null) {			
				type = getVehicleTypeById(request.getId().longValue());
				
				if (type != null && !type.getTypeName().equals(request.getTypeName())) {
					type.setTypeName(request.getTypeName());
					response = vehicleTypeRepository.save(type);
				}			
			}
		}
		return response;
	} 
	
	
	
	/**
	 * @param id
	 * @return
	 */
	public Boolean deleteVehicleType(Long id) {
		
		Boolean response = false;
		VehicleType type = new VehicleType();

		if (id != null) {				
			type = getVehicleTypeById(id);		
			if (type != null) {				
				vehicleTypeRepository.deleteById(id);
				response = true;
			}						
		}
		return response;
	} 

}
