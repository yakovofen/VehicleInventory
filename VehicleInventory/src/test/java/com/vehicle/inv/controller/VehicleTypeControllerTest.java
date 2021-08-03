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
import com.vehicle.inv.model.VehicleType;
import com.vehicle.inv.service.VehicleTypeService;
import com.vehicle.inv.utils.request.VehicleTypeRequest;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = VehicleInventoryApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VehicleTypeControllerTest {

	private static final Logger logger = LogManager.getLogger(VehicleTypeControllerTest.class);
	private static final String PATH = "/v1.0/api/vehicle-type";
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	WebApplicationContext webApplicationContext;
	
	@Autowired
	private VehicleTypeService vehicleTypeService;
	
	ObjectMapper mapper = new ObjectMapper();
	
	
	
	@Test
	public void test01_getAllVehicleTypes() throws Exception {
		
		MvcResult mvcResult = null;
		String uri = PATH + "/all";
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri);

		mvcResult = mvc.perform(request).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
			
		List<VehicleType> content = mapper.readValue(mvcResult.getResponse().getContentAsString(), 
				new TypeReference<List<VehicleType>>() {});

		assertEquals(4, content.size());
		logger.info("test01_getAllVehicleTypes done");
	}
	

	@Test
	public void test02_createVehicleType() throws Exception {
		
		MvcResult mvcResult = null;
		String uri = PATH + "/create";
		
		VehicleTypeRequest vehicleTypeRequest = new VehicleTypeRequest();
		vehicleTypeRequest.setTypeName("Motorcycle");
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(vehicleTypeRequest));

		mvcResult = mvc.perform(request).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		
		List<VehicleType> content = vehicleTypeService.getAllVehicleTypes();
		assertEquals(5, content.size());
		logger.info("test02_createVehicleType done");
	}
	
	
	@Test
	public void test03_getVehicleTypeById() throws Exception {
		
		MvcResult mvcResult = null;
		String uri = PATH + "/2";
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri);
		mvcResult = mvc.perform(request).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);		
		
		String content = mvcResult.getResponse().getContentAsString();
		VehicleType type = mapper.readValue(content, VehicleType.class);
		
		assertEquals("Pickup", type.getTypeName());
		logger.info("test03_getVehicleTypeById done");
	}
	
	
	
	
	@Test
	public void test04_updateVehicleType() throws Exception {
		
		MvcResult mvcResult = null;
		String uri = PATH + "/update";
		
		VehicleTypeRequest vehicleTypeRequest = new VehicleTypeRequest();
		vehicleTypeRequest.setId(4);
		vehicleTypeRequest.setTypeName("Vans");
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(uri)
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(vehicleTypeRequest));

		mvcResult = mvc.perform(request).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
			
		String content = mvcResult.getResponse().getContentAsString();
		VehicleType type = mapper.readValue(content, VehicleType.class);		
		assertEquals("Vans", type.getTypeName());
		logger.info("test04_updateVehicleType done");
	}
	
	
	
	
	@Test
	public void test05_deleteVehicleType() throws Exception {
		
		MvcResult mvcResult = null;
		String uri = PATH + "/delete/3";
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(uri);

		mvcResult = mvc.perform(request).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		
		List<VehicleType> content = vehicleTypeService.getAllVehicleTypes();
		assertEquals(4, content.size());
		logger.info("test05_deleteVehicleType done");
	}
	
	
}
