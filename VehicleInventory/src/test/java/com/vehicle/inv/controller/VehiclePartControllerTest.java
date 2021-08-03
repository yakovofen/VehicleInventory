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
import com.vehicle.inv.model.VehiclePart;
import com.vehicle.inv.service.VehiclePartService;
import com.vehicle.inv.utils.request.VehiclePartRequest;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = VehicleInventoryApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VehiclePartControllerTest {


	private static final Logger logger = LogManager.getLogger(VehiclePartControllerTest.class);
	private static final String PATH = "/v1.0/api/vehicle-part";
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	WebApplicationContext webApplicationContext;
	
	@Autowired
	private VehiclePartService vehiclePartService; 
	
	ObjectMapper mapper = new ObjectMapper();
	
	
	
	@Test
	public void test01_getAllVehicleParts() throws Exception {
		
		MvcResult mvcResult = null;
		String uri = PATH + "/all";
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri);

		mvcResult = mvc.perform(request).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
			
		List<VehiclePart> content = mapper.readValue(mvcResult.getResponse().getContentAsString(), 
				new TypeReference<List<VehiclePart>>() {});

		assertEquals(10, content.size());
		logger.info("test01_getAllVehicleParts done");
	}
	

	@Test
	public void test02_createVehiclePart() throws Exception {
		
		MvcResult mvcResult = null;
		String uri = PATH + "/create";
		
		VehiclePartRequest vehiclePartRequest = new VehiclePartRequest();
		vehiclePartRequest.setPartName("Spare wheel");
		vehiclePartRequest.setVehicleType("Passenger car");
			
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(vehiclePartRequest));

		mvcResult = mvc.perform(request).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		logger.info("test02_createVehiclePart done");
	}
	
	
	@Test
	public void test03_getVehiclePartById() throws Exception {
		
		MvcResult mvcResult = null;
		String uri = PATH + "/2";
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri);
		mvcResult = mvc.perform(request).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
				
		String content = mvcResult.getResponse().getContentAsString();
		VehiclePart part = mapper.readValue(content, VehiclePart.class);
		
		assertEquals("Gearbox", part.getName());
		logger.info("test03_getVehiclePartById done");
	}
	
	
	
	
	@Test
	public void test04_updateVehiclePart() throws Exception {
		
		MvcResult mvcResult = null;
		String uri = PATH + "/update";
		
		VehiclePartRequest vehiclePartRequest = new VehiclePartRequest();
		vehiclePartRequest.setId(1);
		vehiclePartRequest.setPartName("Leather seat");
		vehiclePartRequest.setVehicleType("Pickup");
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(uri)
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(vehiclePartRequest));

		mvcResult = mvc.perform(request).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		
		
		String content = mvcResult.getResponse().getContentAsString();
		VehiclePart part = mapper.readValue(content, VehiclePart.class);		
		assertEquals("Leather seat", part.getName());
		logger.info("test04_updateVehiclePart done");
	}
	
	
	
	
	@Test
	public void test05_deleteVehiclePart() throws Exception {
		
		MvcResult mvcResult = null;
		String uri = PATH + "/delete/10";
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(uri);

		mvcResult = mvc.perform(request).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		
		List<VehiclePart> parts = vehiclePartService.getAllVehicleParts();
		assertEquals(10, parts.size());
		logger.info("test05_deleteVehiclePart done");
	}

}
