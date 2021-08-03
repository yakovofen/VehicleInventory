package com.vehicle.inv.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.vehicle.inv.utils.Utilities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "vehicle")
@ApiModel(description = "All details about vehicle entity.")
public class Vehicle {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "The database generated id")
	private Long id;

	@Column(name = "vin", nullable = false, length = 20)
	@ApiModelProperty(notes = "Vehicle Identification Number of the vehicle")
	private String vin;
	
	@Column(name = "plate_number", nullable = false, length = 10)
	@ApiModelProperty(notes = "Plate number of the vehicle")
	private String plateNumber;
	
	@JsonFormat(pattern = Utilities.DATE_PATTERN)
	@Temporal(TemporalType.DATE)
	@Column(name = "registration_date", nullable = false)
	@ApiModelProperty(notes = "Registartion date of the vehicle")
	private Date registrationDate;
		
	@ManyToOne
	@JoinColumn(name="vehicle_type_id", nullable = false)
	@JsonBackReference
	@ApiModelProperty(notes = "Relation to vehicle type")
	private VehicleType vehicleType;
	
	
	public Vehicle() {
		
	}


	public Vehicle(String vin, String plateNumber, Date registrationDate, VehicleType vehicleType) {
		super();
		this.vin = vin;
		this.plateNumber = plateNumber;
		this.registrationDate = registrationDate;
		this.vehicleType = vehicleType;
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
	 * @return the vin
	 */
	public String getVin() {
		return vin;
	}


	/**
	 * @param vin the vin to set
	 */
	public void setVin(String vin) {
		this.vin = vin;
	}


	/**
	 * @return the plateNumber
	 */
	public String getPlateNumber() {
		return plateNumber;
	}


	/**
	 * @param plateNumber the plateNumber to set
	 */
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}


	/**
	 * @return the registrationDate
	 */
	public Date getRegistrationDate() {
		return registrationDate;
	}


	/**
	 * @param registrationDate the registrationDate to set
	 */
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}


	/**
	 * @return the vehicleType
	 */
	public VehicleType getVehicleType() {
		return vehicleType;
	}


	/**
	 * @param vehicleType the vehicleType to set
	 */
	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}
	
	
}
