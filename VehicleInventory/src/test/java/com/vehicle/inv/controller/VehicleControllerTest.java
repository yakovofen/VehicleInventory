package com.vehicle.inv.controller;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicle.inv.VehicleInventoryApplication;
import com.vehicle.inv.model.Vehicle;
import com.vehicle.inv.service.VehicleService;
import com.vehicle.inv.utils.request.VehicleRequest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VehicleInventoryApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VehicleControllerTest {

	private static final Logger logger = LogManager.getLogger(VehicleControllerTest.class);
	private static final String PATH = "/v1.0/api/vehicle";
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	WebApplicationContext webApplicationContext;
	
	@Autowired
	private VehicleService vehicleService;
	
	ObjectMapper mapper = new ObjectMapper();
	
	
	
	@Test
	public void test01_getAllVehicles() throws Exception {

		MvcResult mvcResult = null;
		String uri = PATH + "/all";
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri);

		mvcResult = mvc.perform(request).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		
		String response = mvcResult.getResponse().getContentAsString();			
		List<Vehicle> content = mapper.readValue(response, 
				new TypeReference<List<Vehicle>>() {});

		assertEquals(5, content.size());
		logger.info("test01_getAllVehicles done");
	}
	

	@Test
	public void test02_createVehicle() throws Exception {
		
		MvcResult mvcResult = null;
		String uri = PATH + "/create";
		
		
		//String resourceName = "classpath:vehicles_test_data.json";
		//File filePath = resourceLoader.getResource(resourceName).getFile();
		//String jsonString = mapper.writeValueAsString(mapper.readValue(filePath, Object.class));

		//MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
		//		.content(jsonString);
		
		
		VehicleRequest vehicleRequest = new VehicleRequest();
		vehicleRequest.setPlateNumber("999-99-789");
		vehicleRequest.setRegistrationDate("2010-09-09");
		vehicleRequest.setVin("dfk865434sd6a4jj");
		vehicleRequest.setVehicleType("Passenger car");
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(vehicleRequest));

		mvcResult = mvc.perform(request).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		logger.info("test02_createVehicle done");
	}
	

	@Test
	public void test03_getVehicleById() throws Exception {
		
		MvcResult mvcResult = null;
		String uri = PATH + "/2";
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri);
		mvcResult = mvc.perform(request).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
				
		String response = mvcResult.getResponse().getContentAsString();
		Vehicle vehicle = mapper.readValue(response, Vehicle.class);
		
		assertEquals("11asddf445d6a4syu", vehicle.getVin());
		logger.info("test04_getVehicleById done");
	}
	
	
	
	
	@Test
	public void test04_updateVehicle() throws Exception {
		
		MvcResult mvcResult = null;
		String uri = PATH + "/update";
		
		VehicleRequest vehicleRequest = new VehicleRequest();
		vehicleRequest.setId(2);
		vehicleRequest.setPlateNumber("000-00-000");
		vehicleRequest.setRegistrationDate("2000-11-11");
		vehicleRequest.setVin("p3asd8458796a4sc");
		vehicleRequest.setVehicleType("Bus");
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(uri)
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(vehicleRequest));

		mvcResult = mvc.perform(request).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		
		Vehicle vehicle = vehicleService.getVehicleById(2L);		
		assertEquals("Bus", vehicle.getVehicleType().getTypeName());
		logger.info("test05_updateVehicle done");
	}
	
	
	@Test
	public void test05_deleteVehicle() throws Exception {
		
		MvcResult mvcResult = null;
		String uri = PATH + "/delete/1";
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(uri);

		mvcResult = mvc.perform(request).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		
		List<Vehicle> content = vehicleService.getAllVehicles();
		assertEquals(5, content.size());
		logger.info("test06_deleteVehicle done");
	}

}
