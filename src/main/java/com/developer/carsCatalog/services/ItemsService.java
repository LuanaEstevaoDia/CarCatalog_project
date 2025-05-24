package com.developer.carsCatalog.services;

import java.io.Console;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.developer.carsCatalog.entities.Items;
import com.developer.carsCatalog.exceptions.ItemNotFoundException;
import com.developer.carsCatalog.repositories.ItemsRepository;

import jakarta.transaction.Transactional;

@Service
public class ItemsService {
	
	private static final Logger logger = LoggerFactory.getLogger(CarsService.class);
	
	@Autowired
	ItemsRepository itemsRepository;
	
	
	public Items saveItem(Items item) {
		logger.info("Item para salvar");
		return itemsRepository.save(item);
		
		
		
	}
	
	public List<Items> findAll(){
		return itemsRepository.findAll();
	}
	
	public Items  findByIdOrThrow(Long id) {
		return itemsRepository.findById(id)
				.orElseThrow(() -> new ItemNotFoundException("Item não encontrado com o ID: " + id));
		
	}
	
	
	@Transactional
	public Items updateItems(Items item ,Long id) {
		Items existingItem = findByIdOrThrow(id);
		
		existingItem.setName(item.getName());
		existingItem.setDescription(item.getDescription());
		
		return itemsRepository.save(existingItem);
			
			
		}
	
	public void deleteItemById(Long id) {
		 findByIdOrThrow(id);
		 
		 itemsRepository.deleteById(id);
	}
		
	}
	


