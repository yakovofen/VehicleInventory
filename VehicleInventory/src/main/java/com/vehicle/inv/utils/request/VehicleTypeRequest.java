package com.vehicle.inv.utils.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VehicleTypeRequest {
	
		
	@JsonProperty("id")	
	private Integer id;

	@JsonProperty("typeName")	
	private String typeName;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
