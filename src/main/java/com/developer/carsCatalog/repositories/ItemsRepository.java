package com.developer.carsCatalog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.developer.carsCatalog.entities.Items;
import com.developer.carsCatalog.entities.Make;

public interface ItemsRepository extends JpaRepository<Items, Long>   {
	
	Optional<Items> findByName(String name);
	Optional<Items> findById(Items id);
	
	
	

}
