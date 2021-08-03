package com.vehicle.inv.service;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vehicle.inv.model.VehiclePart;
import com.vehicle.inv.model.VehicleType;
import com.vehicle.inv.repository.VehiclePartRepository;
import com.vehicle.inv.utils.request.VehiclePartRequest;

@Service
public class VehiclePartService {
	
	
	@Autowired
	VehiclePartRepository vehiclePartRepository;
	
	@Autowired
	VehicleTypeService vehicleTypeService;
	
	
	/**
	 * @param id
	 * @return
	 */
	public VehiclePart getVehiclePartById(Long id) {
		
		VehiclePart part = null;
		Optional<VehiclePart> optional = vehiclePartRepository.findById(id);
		
		if(optional.isPresent()) {
			 part = optional.get();
		}
		return part;
	}
	
	
	/**
	 * @param name
	 * @return
	 */
	public VehiclePart getVehiclePartByName(String name) {
		
		VehiclePart part = null;
		Optional<VehiclePart> optional = vehiclePartRepository.findByName(name);
		
		if(optional.isPresent()) {
			 part = optional.get();
		}
		return part;
	}
	
	
	
	/**
	 * @param id
	 * @return
	 */
	public List<VehiclePart> getAllVehicleParts() {
		
		List<VehiclePart> partsList = new ArrayList<VehiclePart>();
		partsList = (List<VehiclePart>) vehiclePartRepository.findAll();
		return partsList;
	}
	
	
	/**
	 * @param typeName
	 * @return
	 */
	public VehiclePart createVehiclePart(VehiclePartRequest request, VehicleType type) {

		VehiclePart part = null;
				
		if(request.getPartName() != null) {				
			part = getVehiclePartByName(request.getPartName());

			if(part == null) {				
				part = new VehiclePart();				
				part.setName(request.getPartName());
				
				Set<VehicleType> typesList = new HashSet<VehicleType>();	
				typesList.add(type);	
				part.setVehicleTypeList(typesList);
				vehiclePartRepository.save(part);
			}
		}
		return part;
	} 
	
	
	
	/**
	 * @param request
	 * @return
	 * @throws MalformedURLException
	 */
	public VehiclePart updateVehiclePart(VehiclePart vehiclePart, VehiclePartRequest request) {
		
		vehiclePart.setName(request.getPartName());		
		Set<VehicleType> vehicleTypes = vehiclePart.getVehicleTypeList();				
		VehicleType type = vehicleTypeService.getVehicleTypeByName(request.getVehicleType());
		if(type != null) {
			vehicleTypes.add(type);
			vehiclePart.setVehicleTypeList(vehicleTypes);
			vehiclePartRepository.save(vehiclePart);
		}
		return vehiclePart;
	} 
	
	
	
	/**
	 * @param id
	 * @return
	 */
	public Boolean deleteVehiclePart(Long id) {
		
		Boolean response = false;
		VehiclePart part = new VehiclePart();

		if (id != null) {				
			part = getVehiclePartById(id);		
			if (part != null) {				
				vehiclePartRepository.delete(part);
				response = true;
			}						
		}
		return response;
	} 

}
