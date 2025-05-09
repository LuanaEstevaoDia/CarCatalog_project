package com.developer.carsCatalog.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developer.carsCatalog.entities.Make;
import com.developer.carsCatalog.repositories.MakeRepository;

@Service
public class MakeService {

	@Autowired
	private MakeRepository makeRepository;
	
	public Make findByNameOrCreate(Make  make) {
		if(make.getName()== null || make.getName().isEmpty()){
			throw new IllegalArgumentException("O Nome da marca é obrigatório");
			
		}
		Optional<Make> existingMake = makeRepository.findByName(make.getName());
		return existingMake.orElseGet(() -> makeRepository.save(make));
		
	}
	
	public List<Make> findAll(){
		return makeRepository.findAll();
		
	}
	
	public Make findByIdOrThrow(Long id){
		return makeRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Marca não encontrada com o Id" + id));
		
	}
	@Transactional
	public Make updateMake(Long id, Make updatedMake) {
		Make existingMake = findByIdOrThrow(id);
		existingMake.setName(updatedMake.getName());
		existingMake.setCnpj(updatedMake.getCnpj());
		return makeRepository.save(existingMake);
	}
	
	public Map<String, String> delete(Long id) {
		findByIdOrThrow(id);
		makeRepository.deleteById(id);
		Map<String, String> response = new HashMap<>();
		response.put("message", "Carro deletado com sucesso!");
	
		return response;
	
	}

} 
