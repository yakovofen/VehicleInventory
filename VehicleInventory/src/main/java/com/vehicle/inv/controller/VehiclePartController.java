package com.vehicle.inv.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.vehicle.inv.model.VehiclePart;
import com.vehicle.inv.model.VehicleType;
import com.vehicle.inv.service.VehiclePartService;
import com.vehicle.inv.service.VehicleTypeService;
import com.vehicle.inv.utils.request.VehiclePartRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1.0/api/vehicle-part")
@Api(description = "Vehicle Part Controller")
public class VehiclePartController {
	

	@Autowired
	VehiclePartService vehiclePartService;
		
	@Autowired
	VehicleTypeService vehicleTypeService;
	

	@ApiOperation(value = "Get all vehicle parts", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@RequestMapping(value = "/all", method = { RequestMethod.GET })
	public ResponseEntity<Object> getAllVehiclePart() {

		ResponseEntity<Object> response = null;
		List<VehiclePart> result = vehiclePartService.getAllVehicleParts();

		if (!result.isEmpty()) {
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(
					"No Vehicle parts were found",
					HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	
	@ApiOperation(value = "Get vehicle part", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@RequestMapping(value = "/{id}", method = { RequestMethod.GET })
	public ResponseEntity<Object> getVehiclePart(@PathVariable("id") Long id) {

		ResponseEntity<Object> response = null;
		
		if(id != null) {
			VehiclePart result = vehiclePartService.getVehiclePartById(id);	
			if (result != null) {
				response = new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				response = new ResponseEntity<>(
						"Vehicle part with id " + id + " was not found",
						HttpStatus.NOT_FOUND);
			}
		}else {
			response = new ResponseEntity<>(
					"Error: Not a valid request",
					HttpStatus.BAD_REQUEST);
		}	
		return response;
	}
	
	
	
	@ApiOperation(value = "Create vehicle part", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully created"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@RequestMapping(value = "/create", method = { RequestMethod.POST })
	public ResponseEntity<Object> createVehiclePart(@RequestBody VehiclePartRequest request) {

		ResponseEntity<Object> response = null;
		
		if(request.getVehicleType() != null) {		
			VehicleType type = vehicleTypeService.getVehicleTypeByName(request.getVehicleType());
			if(type != null) {				
				VehiclePart result = vehiclePartService.createVehiclePart(request, type);
				if (result != null) {
					response = new ResponseEntity<>(result, HttpStatus.OK);
				} else {
					response = new ResponseEntity<>(
							"Error: Unable to create vehicle part",
							HttpStatus.BAD_REQUEST);
				}										
			}else {
				response = new ResponseEntity<>(
						"Error: Vehicle type " + request.getVehicleType() + " was not found",
						HttpStatus.NOT_FOUND);
			}				
		} else {
			response = new ResponseEntity<>(
					"Error: Not a valid request",
					HttpStatus.BAD_REQUEST);
		}				
		return response;
	}
	
	
	@ApiOperation(value = "Update vehicle part", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@RequestMapping(value = "/update", method = { RequestMethod.PUT })
	public ResponseEntity<Object> updateVehiclePart(@RequestBody VehiclePartRequest request) {

		ResponseEntity<Object> response = null;

		if(request.getId() != null) {			
			VehiclePart vehiclePart = vehiclePartService.getVehiclePartById(request.getId().longValue());
			if (vehiclePart != null) {
				
				VehiclePart result = vehiclePartService.updateVehiclePart(vehiclePart, request);
				if (result != null) {
					response = new ResponseEntity<>(result, HttpStatus.OK);
				} else {
					response = new ResponseEntity<>(
							"Error: Unable to update vehicle part",
							HttpStatus.BAD_REQUEST);
				}
			} else {
				response = new ResponseEntity<>(
						"Error: Vehicle part id " + request.getId() + " was not found",
						HttpStatus.NOT_FOUND);
			}			
		} else {
			response = new ResponseEntity<>(
					"Error: Not a valid request",
					HttpStatus.BAD_REQUEST);
		}	
		return response;
	}
	
	
	@ApiOperation(value = "Delete vehicle part", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@RequestMapping(value = "/delete/{id}", method = { RequestMethod.DELETE })
	public ResponseEntity<Object> deleteVehiclePart(@PathVariable("id") Long id) {

		ResponseEntity<Object> response = null;	
		
		if(id != null) {
			VehiclePart part = vehiclePartService.getVehiclePartById(id);
			if(part != null) {						
				Boolean result = vehiclePartService.deleteVehiclePart(id);
				if (result) {
					response = new ResponseEntity<>("Vehicle part was deleted succssefully", HttpStatus.OK);
				} else {
					response = new ResponseEntity<>(
							"Error: Unable to delete vehicle part",
							HttpStatus.BAD_REQUEST);
				}
			}else{
				response = new ResponseEntity<>(
						"Error: Vehicle part id " + id + " was not found",
						HttpStatus.NOT_FOUND);
			}
		}else{
			response = new ResponseEntity<>(
					"Error: Not a valid request",
					HttpStatus.NOT_FOUND);
		}
		return response;
	}

}
