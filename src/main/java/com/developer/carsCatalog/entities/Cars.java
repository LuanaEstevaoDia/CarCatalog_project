package com.developer.carsCatalog.entities;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cars implements Serializable {
	private static final long serialVersionUID = 1L;
	

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String model;
	private Integer years;
	private String chassi;
	private Double price;
	
	
	
	
	
	

	
	@ManyToOne
	private Make make;
//
//	@ManyToMany(cascade = CascadeType.ALL)
//	@JoinTable(name="car_owners")
//	private List<Owner> owners;
//	
  // public Make getMake() {
    //	return make;
   	
   
	}
//
//	@Enumerated(EnumType.STRING)
//    private StatusCar status;

	
	

