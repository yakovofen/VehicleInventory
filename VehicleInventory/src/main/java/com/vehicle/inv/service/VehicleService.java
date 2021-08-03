package com.vehicle.inv.service;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vehicle.inv.model.Vehicle;
import com.vehicle.inv.model.VehicleType;
import com.vehicle.inv.repository.VehicleRepository;
import com.vehicle.inv.utils.Utilities;
import com.vehicle.inv.utils.request.VehicleRequest;
import com.vehicle.inv.utils.response.VehicleResponse;

@Service
public class VehicleService {
	
	@Autowired
	VehicleRepository vehicleRepository;
	
	@Autowired
	VehicleTypeService vehicleTypeService;
	
	/**
	 * @param id
	 * @return
	 */
	public Vehicle getVehicleById(Long id) {
		
		Vehicle vehicle = null;
		Optional<Vehicle> optional = vehicleRepository.findById(id);
		
		if(optional.isPresent()) {
			vehicle = optional.get();
		}
		return vehicle;
	}
	
	
	/**
	 * @param name
	 * @return
	 */
	public Vehicle getVehicleByVin(String vin) {
		
		Vehicle vehicle = null;
		Optional<Vehicle> optional = vehicleRepository.findByVin(vin);
		
		if(optional.isPresent()) {
			vehicle = optional.get();
		}
		return vehicle;
	}
	
	
	/**
	 * @param name
	 * @return
	 */
	public List<Vehicle> getVehicleByType(VehicleType type) {
		
		List<Vehicle> vehiclesList = new ArrayList<Vehicle>();
		vehiclesList = vehicleRepository.findByVehicleType(type);
		return vehiclesList;
	}
	
	
	
	/**
	 * @param id
	 * @return
	 */
	public List<Vehicle> getAllVehicles() {
		
		List<VehicleResponse> vehiclesList = new ArrayList<VehicleResponse>();
		List<Vehicle> vehicles = (List<Vehicle>) vehicleRepository.findAll();
		
		if(!vehicles.isEmpty()) {
			for(Vehicle v : vehicles) {
				VehicleResponse res = new VehicleResponse();
				res.setId(v.getId().intValue());
				res.setVin(v.getVin());
				res.setPlateNumber(v.getPlateNumber());
				res.setRegistrationDate(v.getRegistrationDate());
				res.setVehicleType(v.getVehicleType());
				vehiclesList.add(res);
			}
		}
		return vehicles;
	}
	
	
	/**
	 * @param typeName
	 * @return
	 * @throws ParseException 
	 */
	public Vehicle createVehicle(VehicleRequest request, VehicleType type) throws ParseException {

		Vehicle vehicle = null;
		
		Optional<Vehicle> vehicleByVin = vehicleRepository.findByPlateNumberOrVin(request.getPlateNumber(), request.getVin());
		if (!vehicleByVin.isPresent()) {	
			vehicle = new Vehicle();
			vehicle.setPlateNumber(request.getPlateNumber());
			vehicle.setVin(request.getVin());			
			vehicle.setRegistrationDate(Utilities.yyyyMMdd.parse(request.getRegistrationDate()));								
			vehicle.setVehicleType(type);
			vehicleRepository.save(vehicle);
		}				
		return vehicle;
	} 
	
	
	
	/**
	 * @param request
	 * @return
	 * @throws ParseException 
	 * @throws MalformedURLException
	 */
	public Vehicle updateVehicle(Vehicle vehicle, VehicleRequest request) throws ParseException {

		vehicle.setPlateNumber(request.getPlateNumber());
		vehicle.setRegistrationDate(Utilities.yyyyMMdd.parse(request.getRegistrationDate()));
		vehicle.setVin(request.getVin());
		
		String vehicleType = vehicle.getVehicleType().getTypeName();		
		if(!vehicleType.equals(request.getVehicleType())) {
			VehicleType type = vehicleTypeService.getVehicleTypeByName(request.getVehicleType());
			if(type != null) {
				vehicle.setVehicleType(type);
				vehicleRepository.save(vehicle);
			}
		}		
		return vehicle;
	} 
	
	
	
	/**
	 * @param id
	 * @return
	 */
	public Boolean deleteVehicle(Long id) {
		
		Boolean response = false;			
		vehicleRepository.deleteById(id);
		response = true;
		return response;
	} 

}
