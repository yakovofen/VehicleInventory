package com.vehicle.inv.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "vehicle_type")
@ApiModel(description = "All details about vehicle type entity.")
public class VehicleType {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "The database generated id")
	private Long id;

	@Column(name = "type_name", nullable = false, length = 100)
	@ApiModelProperty(notes = "Vehicle type name")
	private String typeName;
		
	@ManyToMany(cascade = CascadeType.DETACH)
	@JoinTable( 
			  name = "vtype_vpart_relations", 
			  joinColumns = @JoinColumn(name = "vehicle_type_id"), 
			  inverseJoinColumns = @JoinColumn(name = "vehicle_part_id"))
	@ApiModelProperty(notes = "Relation between vehicle type and part")
	private List<VehiclePart> vehiclePartList;	
	
	@OneToMany(mappedBy="vehicleType", cascade = CascadeType.ALL, orphanRemoval = true)
	@ApiModelProperty(notes = "Relation to vehicles")
	private List<Vehicle> vehicleList;


	
	public VehicleType() {
		
	}



	public VehicleType(String typeName, List<VehiclePart> vehiclePartList, List<Vehicle> vehicleList) {
		super();
		this.typeName = typeName;
		this.vehiclePartList = vehiclePartList;
		this.vehicleList = vehicleList;
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



	/**
	 * @return the vehiclePartList
	 */
	public List<VehiclePart> getVehiclePartList() {
		return vehiclePartList;
	}



	/**
	 * @param vehiclePartList the vehiclePartList to set
	 */
	public void setVehiclePartList(List<VehiclePart> vehiclePartList) {
		this.vehiclePartList = vehiclePartList;
	}



	/**
	 * @return the vehicleList
	 */
	public List<Vehicle> getVehicleList() {
		return vehicleList;
	}



	/**
	 * @param vehicleList the vehicleList to set
	 */
	public void setVehicleList(List<Vehicle> vehicleList) {
		this.vehicleList = vehicleList;
	}
	

}
