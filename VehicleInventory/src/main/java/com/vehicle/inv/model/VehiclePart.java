package com.vehicle.inv.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "vehicle_part")
@ApiModel(description = "All details about vehicle part entity.")
public class VehiclePart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "The database generated id")
	private Long id;

	@Column(name = "name", nullable = false, length = 100)
	@ApiModelProperty(notes = "Vehicle part name")
	private String name;
			
	@ManyToMany(cascade = CascadeType.DETACH)
	@JoinTable(
			  name = "vtype_vpart_relations", 
			  joinColumns = @JoinColumn(name = "vehicle_part_id"), 
			  inverseJoinColumns = @JoinColumn(name = "vehicle_type_id"))
	@JsonBackReference
	@ApiModelProperty(notes = "Relation between vehicle type and part")
	private Set<VehicleType> vehicleTypeList;



	
	public VehiclePart() {
		
	}


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the vehicleTypeList
	 */
	public Set<VehicleType> getVehicleTypeList() {
		return vehicleTypeList;
	}


	/**
	 * @param vehicleTypeList the vehicleTypeList to set
	 */
	public void setVehicleTypeList(Set<VehicleType> vehicleTypeList) {
		this.vehicleTypeList = vehicleTypeList;
	}


}
