package com.developer.carsCatalog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developer.carsCatalog.entities.Make;
import com.developer.carsCatalog.exceptions.InvalidDataException;
import com.developer.carsCatalog.exceptions.MakeNotFoundException;
import com.developer.carsCatalog.repositories.CarsRepository;
import com.developer.carsCatalog.repositories.MakeRepository;

@Service
public class MakeService {

	@Autowired
	private MakeRepository makeRepository;
	
	@Autowired
	private CarsRepository carsRepository;
	
    // Injeção de dependência via construtor
    public MakeService(MakeRepository makeRepository, CarsRepository carsRepository) {
        this.makeRepository = makeRepository;
        this.carsRepository = carsRepository;
    }

	
	public Make findByNameOrCreate(Make  make) {
		if(make.getName()== null || make.getName().isEmpty()){
			throw new InvalidDataException("O Nome da marca é obrigatório");
			
		}
		
		validateCnpj(make.getCnpj());
		Optional<Make> existingMake = makeRepository.findByName(make.getName());
		return existingMake.orElseGet(() -> makeRepository.save(make));
		
	}
	
	public List<Make> findAll(){
		return makeRepository.findAll();
		
	}
	
	public Make findByIdOrThrow(Long id){
		return makeRepository.findById(id)
				.orElseThrow(() -> new MakeNotFoundException("Marca não encontrada com o Id" + id));
		
	}
	@Transactional
	public Make updateMake(Long id, Make updatedMake) {
		// Verifica se a marca existe
        Make existingMake = makeRepository.findById(id)
                .orElseThrow(() -> new MakeNotFoundException("Marca não encontrada"));

        // Verifica se há algum veículo vinculado a essa marca
        boolean isLinkedToCar = carsRepository.existsByMakeId(id); 
        if (isLinkedToCar) {
            throw new  IllegalStateException("A marca não pode ser alterada porque já está vinculada a um veículo.");
        }

        // Atualiza os dados da marca
        existingMake.setName(updatedMake.getName());
        validateCnpj(updatedMake.getCnpj());
        existingMake.setCnpj(updatedMake.getCnpj());

        return makeRepository.save(existingMake);
    }


	
	
	public void delete(Long id) {
		findByIdOrThrow(id);
		makeRepository.deleteById(id);
		
	}
	
	private String validateCnpj(String cnpj) {
	if(cnpj == null || !cnpj.matches("\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}")) {
		throw new InvalidDataException("CNPJ inválido. Deve conter 14 dígitos númericos.");
	}
	return cnpj;
	}

} 
