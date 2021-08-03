package com.vehicle.inv.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vehicle.inv.model.Vehicle;
import com.vehicle.inv.model.VehicleType;
import com.vehicle.inv.service.VehicleService;
import com.vehicle.inv.service.VehicleTypeService;
import com.vehicle.inv.utils.request.VehicleRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1.0/api/vehicle")
@Api(description = "Vehicle Controller")
public class VehicleController {
	
	
	@Autowired
	VehicleService vehicleService;
	
	@Autowired
	VehicleTypeService vehicleTypeService;
	
	
	@ApiOperation(value = "Get all vehicles", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@RequestMapping(value = "/all", method = { RequestMethod.GET })
	public ResponseEntity<Object> getAllVehicles() {

		ResponseEntity<Object> response = null;
		List<Vehicle> result = vehicleService.getAllVehicles();

		if (!result.isEmpty()) {
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}else{
			response = new ResponseEntity<>(
					"No Vehicle were found",
					HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	
	@ApiOperation(value = "Get vehicle by type", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@RequestMapping(value = "/{id}", method = { RequestMethod.GET })
	public ResponseEntity<Object> getVehicleById(@PathVariable("id") Long id) {

		ResponseEntity<Object> response = null;
		
		if(id != null) {
			Vehicle result = vehicleService.getVehicleById(id);
	
			if (result != null) {
				response = new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				response = new ResponseEntity<>(
						"Vehicle id " + id + " was not found",
						HttpStatus.NOT_FOUND);
			}
		}else{
			response = new ResponseEntity<>(
					"Error: Not a valid request",
					HttpStatus.BAD_REQUEST);
		}	
		return response;
	}
	
	
	@ApiOperation(value = "Get vehicle by type", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@RequestMapping(value = "/type/{name}", method = { RequestMethod.GET })
	public ResponseEntity<Object> getVehicleByType(@PathVariable("name") String name) {

		ResponseEntity<Object> response = null;

        if(name != null) {
        	VehicleType type = vehicleTypeService.getVehicleTypeByName(name);
        	if(type != null) {
        		List<Vehicle> result = vehicleService.getVehicleByType(type);    		
        		if (result != null) {
        			response = new ResponseEntity<>(result, HttpStatus.OK);
        		} else {
        			response = new ResponseEntity<>(
        					"Vehicle with type " + name + " was not found",
        					HttpStatus.NOT_FOUND);
        		}
        	}else {
    			response = new ResponseEntity<>(
    					"Vehicle type " + name + " was not found",
    					HttpStatus.NOT_FOUND);
    		}       	
        }else{
			response = new ResponseEntity<>(
					"Error: Not a valid request",
					HttpStatus.BAD_REQUEST);
		}			
		return response;
	}
	
	
	
	@ApiOperation(value = "Create vehicle", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully created"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@RequestMapping(value = "/create", method = { RequestMethod.POST })
	public ResponseEntity<Object> createVehicle(@RequestBody VehicleRequest vehicleRequest) throws ParseException {

		ResponseEntity<Object> response = null;		
		if(vehicleRequest.getVehicleType() != null) {		
			VehicleType type = vehicleTypeService.getVehicleTypeByName(vehicleRequest.getVehicleType());
			if(type != null) {				
				Vehicle result = vehicleService.createVehicle(vehicleRequest, type);
				if (result != null) {
					response = new ResponseEntity<>(result, HttpStatus.OK);
				} else {
					response = new ResponseEntity<>(
							"Error: Unable to create vehicle",
							HttpStatus.BAD_REQUEST);
				}										
			}else{
				response = new ResponseEntity<>(
						"Error: Vehicle type " + vehicleRequest.getVehicleType() + " was not found",
						HttpStatus.NOT_FOUND);
			}				
		}else{
			response = new ResponseEntity<>(
					"Error: Not a valid request",
					HttpStatus.BAD_REQUEST);
		}				
		return response;
	}
	
	
	@ApiOperation(value = "Update vehicle", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@RequestMapping(value = "/update", method = { RequestMethod.PUT })
	public ResponseEntity<Object> updateVehicleType(@RequestBody VehicleRequest vehicleRequest) throws ParseException {

		ResponseEntity<Object> response = null;	
		
		if(vehicleRequest.getId() != null) {		
			Vehicle vehicle = vehicleService.getVehicleById(vehicleRequest.getId().longValue());
			if (vehicle != null) {			
				Vehicle result = vehicleService.updateVehicle(vehicle, vehicleRequest);
				if (result != null) {
					response = new ResponseEntity<>(result, HttpStatus.OK);
				}else{
					response = new ResponseEntity<>(
							"Error: Unable to update vehicle",
							HttpStatus.BAD_REQUEST);
				}
			}else{
				response = new ResponseEntity<>(
						"Error: Vehicle id " + vehicleRequest.getId() + " was not found",
						HttpStatus.NOT_FOUND);
			}			
		}else{
			response = new ResponseEntity<>(
					"Error: Not a valid request",
					HttpStatus.BAD_REQUEST);
		}	
		return response;
	}
	
	
	@ApiOperation(value = "Delete vehicle", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted"),
			@ApiResponse(code = 401, message = "You are not authorized to delete the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@RequestMapping(value = "/delete/{id}", method = { RequestMethod.DELETE })
	public ResponseEntity<Object> deleteVehicle(@PathVariable("id") Long id) {

		ResponseEntity<Object> response = null;	
		
		if(id != null) {
			Vehicle vehicle = vehicleService.getVehicleById(id);
			if(vehicle != null) {			
				Boolean result = vehicleService.deleteVehicle(id);
				if (result) {
					response = new ResponseEntity<>("Vehicle was deleted succssefully", HttpStatus.OK);
				} else {
					response = new ResponseEntity<>(
							"Error: Unable to delete vehicle",
							HttpStatus.BAD_REQUEST);
				}
			}else{
				response = new ResponseEntity<>(
						"Error: Vehicle id " + id + " was not found",
						HttpStatus.NOT_FOUND);
			}	
		}else{
			response = new ResponseEntity<>(
					"Error: Not a valid request",
					HttpStatus.BAD_REQUEST);
		}	
		return response;
	}

}
