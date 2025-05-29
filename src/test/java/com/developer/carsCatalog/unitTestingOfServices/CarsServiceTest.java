package com.developer.carsCatalog.unitTestingOfServices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import com.developer.carsCatalog.entities.Cars;
import com.developer.carsCatalog.entities.Make;
import com.developer.carsCatalog.exceptions.validation.InvalidCarModelException;
import com.developer.carsCatalog.exceptions.validation.InvalidCarYearException;
import com.developer.carsCatalog.exceptions.validation.invalidateChassisByQuantityAndCharacter;
import com.developer.carsCatalog.exceptions.validation.invalidateDuplicateChassisCarException;
import com.developer.carsCatalog.repositories.CarsRepository;
import com.developer.carsCatalog.repositories.MakeRepository;
import com.developer.carsCatalog.services.CarsService;
import com.developer.carsCatalog.utils.CarValidationMessages;

@ActiveProfiles("test")
public class CarsServiceTest {

	@InjectMocks
	private CarsService carsService;

	@Mock
	CarsRepository carsRepository;

	@Mock
	MakeRepository makeRepository;

	List<Cars> carsList = new ArrayList<>();

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		Cars existingCar = Cars.builder().model("Corolla").years(2020).chassi("1NXBR32E76Z1234PQ").build();
		carsList.add(existingCar); // Adicionando um carro com chassi já existente

	}

	@Test
	public void shouldThrowExceptionWhenModelIsNullOrEmpty() {

		Make make = Make.builder().name("Toyota").cnpj("CNPJ: 45.678.912/0001-34").build();

		Cars car = Cars.builder().model("").years(2020).chassi("1NXBR32E76Z123456").make(make).build();

		Exception exception = assertThrows(InvalidCarModelException.class, () -> {
			carsService.validateCar(car, carsList);

		});
		assertEquals(CarValidationMessages.INVALID_MODEL.getMessage(), exception.getMessage());
	}

	@Test
	public void shouldThrowExceptionWhenYearIsInvalid() {
		int currentYear = LocalDate.now().getYear();

		Make make = Make.builder().name("Toyota").cnpj("CNPJ: 45.678.912/0001-34").build();

		Cars car = Cars.builder().model("Corolla").years(currentYear + 1).chassi("1NXBR32E76Z123456").make(make)
				.build();

		Exception exception = assertThrows(InvalidCarYearException.class, () -> {
			carsService.validateCar(car, carsList);
		});

		assertEquals(CarValidationMessages.INVALID_YEAR.getMessage(), exception.getMessage());

	}

	@Test
	public void shouldThrowExceptionWhenChassiIsInvalid() {

		Make make = Make.builder().name("Toyota").cnpj("CNPJ: 45.678.912/0001-34").build();

		Cars car = Cars.builder().model("Corolla").years(2020).chassi("123456").make(make).build();

		Exception exception = assertThrows(invalidateChassisByQuantityAndCharacter.class, () -> {
			carsService.validateCar(car, carsList);
		});
		assertEquals(CarValidationMessages.INVALID_CHASSI_ERROR.getMessage(), exception.getMessage());

	}

	@Test
	public void shouldThrowExceptionWhenChassiIsDuplicate() {

		Make make = Make.builder().name("Toyota").cnpj("CNPJ: 45.678.912/0001-34").build();

		Cars car = Cars.builder().model("Corolla").years(2020).chassi("1NXBR32E76Z1234PQ").make(make).build();
		

		carsList.add(car);

		Exception exception = assertThrows(invalidateDuplicateChassisCarException.class, () -> {
			carsService.validateCar(car, carsList);

		});

		assertEquals(CarValidationMessages.INVALID_CHASSI.getMessage(), exception.getMessage());

	}

	@Test
	public void shouldPassValidationForValidCar() {

		Make make = Make.builder().name("Toyota").cnpj("CNPJ: 45.678.912/0001-34").build();

		Cars car = Cars.builder().model("Corolla").years(2020).chassi("1NXBR32E76Z123456").make(make).build();

		when(carsRepository.existsByChassi(car.getChassi())).thenReturn(false);

		carsService.validateCar(car, carsList);

	}

}
