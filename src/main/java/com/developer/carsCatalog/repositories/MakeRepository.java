package com.developer.carsCatalog.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.developer.carsCatalog.entities.Make;

public interface MakeRepository extends JpaRepository<Make, Long>{

	Optional<Make> findByName(String name);
	
	
	
	Optional<Make> findById(Make id);
	
	
	
	

}
