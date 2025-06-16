package com.developer.carsCatalog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developer.carsCatalog.entities.Make;
import com.developer.carsCatalog.exceptions.MakeConflictException;
import com.developer.carsCatalog.exceptions.MakeNotFoundException;
import com.developer.carsCatalog.exceptions.validation.InvalidCnpjException;
import com.developer.carsCatalog.exceptions.validation.InvalidMakeNameException;
import com.developer.carsCatalog.exceptions.validation.InvalidateMakeRegisteredNameException;
import com.developer.carsCatalog.repositories.CarsRepository;
import com.developer.carsCatalog.repositories.MakeRepository;
import com.developer.carsCatalog.utils.MakeValidationMessage;

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

	
	public Make findByNameOrCreate(Make make) {
		
		if(make.getName() == null || make.getName().isEmpty()) {
			throw new InvalidMakeNameException(MakeValidationMessage.INVALID_NAME.getMessage());
		}
		invalidateIfMakeAlreadyExists(make);
		validateCnpj(make.getCnpj());
		
		
		return makeRepository.save(make);
			
		}
		
		
	public List<Make> findAll(){
		return makeRepository.findAll();
		
	}
	
	public Make findByIdOrThrow(Long id){
		return makeRepository.findById(id)
				.orElseThrow(() -> new MakeNotFoundException(MakeValidationMessage.MAKE_NOT_FOUND.getMessage() + id));
		
	}
	@Transactional
	public Make updateMake(Long id, Make updatedMake) {
		// Verifica se a marca existe
        Make existingMake = makeRepository.findById(id)
                .orElseThrow(() -> new MakeNotFoundException(MakeValidationMessage.MAKE_NOT_FOUND.getMessage()));

    
        deleteOrUpdateMakeLinkedToCar(id);

       
        existingMake.setName(updatedMake.getName());
        validateCnpj(updatedMake.getCnpj());
        existingMake.setCnpj(updatedMake.getCnpj());

        return makeRepository.save(existingMake);
    }


	
	
	public void delete(Long id) {
		findByIdOrThrow(id);
		deleteOrUpdateMakeLinkedToCar(id);
		makeRepository.deleteById(id);
		
	}
	
	private String validateCnpj(String cnpj) {
	if(cnpj == null || !cnpj.matches("\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}")) {
		throw new InvalidCnpjException(MakeValidationMessage.CNPJ_INVALID_FORMAT.getMessage());
	}
	return cnpj;
	}
	
	private void invalidateIfMakeAlreadyExists(Make make) {
		

	      Optional<Make> registeredName = makeRepository.findByName(make.getName());
	      

	    if (registeredName.isPresent() ) {  
	        throw new InvalidateMakeRegisteredNameException(MakeValidationMessage.DUPLICATE_MAKE_NAME.getMessage(make.getName()));
	    }
	    

		
	}
	
	public void deleteOrUpdateMakeLinkedToCar(Long makeId) {
		
		 boolean isLinkedToCar = carsRepository.existsByMakeId(makeId);
		 
		 if (isLinkedToCar) {
	            throw new  MakeConflictException(MakeValidationMessage.MAKE_CONFLICT.getMessage());
	        }
		 
		
	}

} 
