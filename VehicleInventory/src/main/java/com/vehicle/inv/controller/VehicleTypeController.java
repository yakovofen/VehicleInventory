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
import com.vehicle.inv.model.VehicleType;
import com.vehicle.inv.service.VehicleTypeService;
import com.vehicle.inv.utils.request.VehicleTypeRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1.0/api/vehicle-type")
@Api(description = "Vehicle Type Controller")
public class VehicleTypeController {

	@Autowired
	VehicleTypeService vehicleTypeService;
	
	

	@ApiOperation(value = "Get all vehicle types", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@RequestMapping(value = "/all", method = { RequestMethod.GET })
	public ResponseEntity<Object> getAllVehicleType() {

		ResponseEntity<Object> response = null;
		List<VehicleType> result = vehicleTypeService.getAllVehicleTypes();

		if (!result.isEmpty()) {
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(
					"No Vehicle types were found",
					HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	
	@ApiOperation(value = "Get vehicle type", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@RequestMapping(value = "/{id}", method = { RequestMethod.GET })
	public ResponseEntity<Object> getVehicleType(@PathVariable("id") Long id) {

		ResponseEntity<Object> response = null;
		
		if(id != null) {
			VehicleType result = vehicleTypeService.getVehicleTypeById(id);
			if (result != null) {
				response = new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				response = new ResponseEntity<>(
						"Vehicle type id " + id + " was not found",
						HttpStatus.NOT_FOUND);
			}
		}else {
			response = new ResponseEntity<>(
					"Error: Not a valid request",
					HttpStatus.BAD_REQUEST);
		}	
		return response;
	}
	
	
	
	@ApiOperation(value = "Create vehicle type", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully created"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@RequestMapping(value = "/create", method = { RequestMethod.POST })
	public ResponseEntity<Object> createVehicleType(@RequestBody VehicleTypeRequest vehicleTypeRequest) {

		ResponseEntity<Object> response = null;	
		
		if(vehicleTypeRequest.getTypeName() != null) {
			VehicleType type = vehicleTypeService.getVehicleTypeByName(vehicleTypeRequest.getTypeName());
			if(type == null) {	
				VehicleType result = vehicleTypeService.createVehicleType(vehicleTypeRequest.getTypeName());	
				if (result != null) {
					response = new ResponseEntity<>(result, HttpStatus.OK);
				} else {
					response = new ResponseEntity<>(
							"Error: Unable to create vehicle type",
							HttpStatus.BAD_REQUEST);
				}
			}else {
				response = new ResponseEntity<>(
						"Error: Vehicle type " + vehicleTypeRequest.getTypeName() + " already exist",
						HttpStatus.NOT_FOUND);
			}	
		} else {
			response = new ResponseEntity<>(
					"Error: Not a valid request",
					HttpStatus.BAD_REQUEST);
		}	
		return response;
	}
	
	
	@ApiOperation(value = "Update vehicle type", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@RequestMapping(value = "/update", method = { RequestMethod.PUT })
	public ResponseEntity<Object> updateVehicleType(@RequestBody VehicleTypeRequest request) {

		ResponseEntity<Object> response = null;
		
		if(request.getId() != null) {
			VehicleType result = vehicleTypeService.updateVehicleType(request);	
			if (result != null) {
				response = new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				response = new ResponseEntity<>(
						"Error: Unable to update vehicle",
						HttpStatus.BAD_REQUEST);
			}
		} else {
			response = new ResponseEntity<>(
					"Error: Not a valid request",
					HttpStatus.BAD_REQUEST);
		}	
		return response;
	}
	
	
	@ApiOperation(value = "Delete vehicle type", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@RequestMapping(value = "/delete/{id}", method = { RequestMethod.DELETE })
	public ResponseEntity<Object> deleteVehicleType(@PathVariable("id") Long id) {

		ResponseEntity<Object> response = null;
		
		if(id != null) {
			Boolean result = vehicleTypeService.deleteVehicleType(id);
			if (result) {
				response = new ResponseEntity<>("Vehicle was deleted succssefully", HttpStatus.OK);
			} else {
				response = new ResponseEntity<>(
						"Error: Unable to delete vehicle",
						HttpStatus.BAD_REQUEST);
			}
		}else{
			response = new ResponseEntity<>(
					"Error: Not a valid request",
					HttpStatus.NOT_FOUND);
		}
		return response;
	}

}
