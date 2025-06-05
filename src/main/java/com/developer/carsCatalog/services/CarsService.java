package com.developer.carsCatalog.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developer.carsCatalog.entities.Cars;
import com.developer.carsCatalog.exceptions.CarNotFoundException;
import com.developer.carsCatalog.exceptions.validation.InvalidCarChassiException;
import com.developer.carsCatalog.exceptions.validation.InvalidCarMakeException;
import com.developer.carsCatalog.exceptions.validation.InvalidCarModelException;
import com.developer.carsCatalog.exceptions.validation.InvalidCarYearException;
import com.developer.carsCatalog.exceptions.validation.invalidateChassisByQuantityAndCharacter;
import com.developer.carsCatalog.exceptions.validation.invalidateDuplicateChassisCarException;
import com.developer.carsCatalog.repositories.CarsRepository;
import com.developer.carsCatalog.repositories.MakeRepository;
import com.developer.carsCatalog.utils.CarValidationMessages;


@Service
public class CarsService {

	private static final Logger logger = LoggerFactory.getLogger(CarsService.class);

	@Autowired
	CarsRepository carsRepository;
	
	@Autowired
	MakeRepository makeRepository;
	
	@Autowired
	MakeService makeService;

	


	public Cars saveCar(Cars car) {
		logger.info("Tentativa de salvar veículo");
		
		
		List<Cars> carsList = carsRepository.findAll();
		validateCar(car, carsList);
		
		Cars savedCar = carsRepository.save(car);
		logger.info("Carro salvo com sucesso.ID gerado" + savedCar.getId());
		
		
		return savedCar; 
	}

	public List<Cars> findAll() {

		return carsRepository.findAll();

	}

	public Cars findByIdOrThrow(Long id) {
		return carsRepository.findById(id)
				.orElseThrow(() -> new CarNotFoundException(CarValidationMessages.CAR_NOT_FOUND.getMessage() + id));
	}

	@Transactional
	public Cars upDateCar(Cars car, Long id) {
		logger.info("Tentando atualizar carro com ID: " + id);
		Cars existingCar = findByIdOrThrow(id);

		if (!existingCar.getChassi().equals(car.getChassi())) {
			throw new InvalidCarChassiException(CarValidationMessages.INVALID_CHANGE_CHASSI.getMessage());
		}

		existingCar.setModel(car.getModel());
		existingCar.setYears(car.getYears());
		existingCar.setPrice(car.getPrice());

		//filtrar para a listagem carregada saiba que o carro existe e só está atualizando alguns dados
		List<Cars> carsList = findAll()
				.stream()
				.filter(c -> !c.getId().equals(existingCar.getId()))
				.collect(Collectors.toList());

		validateCar(existingCar, carsList);
		
		return carsRepository.save(existingCar);
		
	}

	public void deleteById(Long id) {
		findByIdOrThrow(id);
		carsRepository.deleteById(id);
	
		
	}
	public void validateCar(Cars car, List<Cars> carsList) {
		if (car.getModel() == null || car.getModel().isEmpty()) {
			throw new InvalidCarModelException(CarValidationMessages.INVALID_MODEL.getMessage());

		}
		int currentYear = LocalDate.now().getYear();
		if (car.getYears() < 1900 || car.getYears() > currentYear) {
			throw new InvalidCarYearException(CarValidationMessages.INVALID_YEAR.getMessage());

		}

		if (car.getChassi() == null || car.getChassi().length() != 17) {
			throw new invalidateChassisByQuantityAndCharacter(CarValidationMessages.INVALID_CHASSI_ERROR.getMessage());
		}
		
	    if (car.getMake() == null || car.getMake().getName() == null || car.getMake().getName().isEmpty()) {
	        throw new InvalidCarMakeException(CarValidationMessages.INVALID_MAKE.getMessage());
	    }


	   
	    boolean existingCarWithSameChassi = carsList.stream()
	    		.anyMatch(existingCar -> existingCar.getChassi().equals(car.getChassi()));
	    				

       if(existingCarWithSameChassi) {
    	   throw new invalidateDuplicateChassisCarException(CarValidationMessages.INVALID_CHASSI.getMessage());
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
