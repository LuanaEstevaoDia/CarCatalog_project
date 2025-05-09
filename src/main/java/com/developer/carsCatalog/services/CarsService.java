package com.developer.carsCatalog.services;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developer.carsCatalog.entities.Cars;
import com.developer.carsCatalog.entities.Make;
import com.developer.carsCatalog.exceptions.CarNotFoundException;
import com.developer.carsCatalog.repositories.CarsRepository;


@Service
public class CarsService {

	private static final Logger logger = LoggerFactory.getLogger(CarsService.class);

	@Autowired
	CarsRepository carsRepository;
	
	@Autowired
	MakeService makeService;
	

	@Transactional
	public Cars saveCar(Cars car) {
		logger.info("Tentativa de salvar veículo");
		
		logger.info("Carro recebido para salvar" + car);
		logger.info("Id do veículo antes de salvar" + car.getId());
		
		validateCar(car);
		if(car.getMake() != null) {
			 Make savedMake = makeService.findByNameOrCreate(car.getMake());
			 car.setMake(savedMake);
		}
		
		Cars savedCar = carsRepository.save(car);
		logger.info("Carro salvo com sucesso.ID gerado" + savedCar.getId());
		
		
		return savedCar; 
	}

	public List<Cars> findAll() {

		return carsRepository.findAll();

	}

	public Cars findByIdOrThrow(Long id) {
		return carsRepository.findById(id)
				.orElseThrow(() -> new CarNotFoundException("Carro não encontrado com o ID: " + id));
	}

	@Transactional
	public Map<String, String> upDateCar(Cars car, Long id) {
		logger.info("Tentando atualizar carro com ID: " + id);
		Cars existingCar = findByIdOrThrow(id);

		if (!existingCar.getChassi().equals(car.getChassi())) {
			throw new IllegalArgumentException("O número do chassi não pode ser alterado");
		}

		existingCar.setModel(car.getModel());
		existingCar.setYears(car.getYears());
		existingCar.setPrice(car.getPrice());

		// incluir outras regras que possam mudar na atualização

		validateCar(existingCar);
		//carsRepository.save(existingCar);
		
		Map<String, String> response = new HashMap<>();
		response.put("message", "Veículo atualizado com sucesso!");
		return response;
	}

	public Map<String, String> deleteById(Long id) {
		findByIdOrThrow(id);
		carsRepository.deleteById(id);
		Map<String, String> response = new HashMap<>();
		response.put("message", "Carro detetado com sucesso!");
		return response;
	}

//	public List<Cars> findByModel(String model){
//		return this.carsRepository.findByModel(model);
//		
//	}
//	public List<Cars> findByMake(Long idMake){
//		Make make = new Make();
//		make.setId(idMake);
//		return this.carsRepository.findByMake(make);
//}
//
//	public List<Cars> findByCarsYears(int years){
//		return this.carsRepository.findByCarsYears(years);
//		
//	}
	public void validateCar(Cars car) {
		if (car.getModel() == null || car.getModel().isEmpty()) {
			throw new IllegalArgumentException("O campo modelo é obrigatório");

		}
		int currentYear = LocalDate.now().getYear();
		if (car.getYears() < 1900 || car.getYears() > currentYear) {
			throw new IllegalArgumentException("Ano do veículo inválido");

		}

		if (car.getChassi() == null || car.getChassi().length() != 17) {
			throw new IllegalArgumentException("Número de chassi inválido");
		}
		
	    if (car.getMake() == null || car.getMake().getName() == null || car.getMake().getName().isEmpty()) {
	        throw new IllegalArgumentException("A marca do carro é obrigatória");
	    }

        
			if(car.getId() != null) {
		      Cars existingCar = findByIdOrThrow(car.getId());
		       if (!existingCar.getChassi().equals(car.getChassi())) {
		           Optional<Cars> existingCarWithSameChassi = carsRepository.findByChassi(car.getChassi());
		           if (existingCarWithSameChassi.isPresent() &&
		               !existingCarWithSameChassi.get().getId().equals(car.getId())) {
		               throw new IllegalArgumentException("Chassi já existente para outro veículo");
		           }
		       }
		      
			}
			
	}

		   //    }
//		if(car.getStatus() == StatusCar.VENDIDO) {
//			throw new IllegalStateException("Veículo vendido não pode ser atualizado");
	
//	
//		car.setPrice(calculatePrice(car));
//		
//	}
//		
//	public Double calculatePrice(Cars car) {
//		double basePrice = 20000;
//		int currentYear = LocalDate.now().getYear();
//		if(car.getYears()< currentYear - 10) {
//			basePrice *= 0.8;
//		}
//		return basePrice;
//		
//	}

	}

