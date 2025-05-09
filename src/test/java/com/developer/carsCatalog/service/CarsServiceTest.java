/*
  package com.developer.carsCatalog.service;
  
  import static org.junit.jupiter.api.Assertions.assertEquals; import static
  org.junit.jupiter.api.Assertions.assertThrows;
  
  import java.time.LocalDate;
  
  import org.junit.jupiter.api.BeforeEach; import org.junit.jupiter.api.Test;
  import org.springframework.beans.factory.annotation.Autowired;
  
  import com.developer.carsCatalog.entities.Cars; import
  com.developer.carsCatalog.sevices.CarsService;
  
  public class CarsServiceTest {
  
  @Autowired CarsService carsService;
  
  
  @BeforeEach void setUp() { carsService = new CarsService();
  
  invalidCarChassi = new Cars(); invalidCarChassi.setModel("Valid Model");
  invalidCarChassi.setYears(2020); // invalidCarChassi.setChassi("12345");
  
  //} //@Test //void testCalculatePrice_CarYongerThan10Years() { // Cars car =
 new Cars(); // car.setYears(LocalDate.now().getYear() - 5); // Double price =
 * carsService.calculatePrice(car); assertEquals(20000,price); // // } // @Test
 * // void testCalculatePrice_CarOlderThan10Years() { // Cars car = new Cars();
 * // car.setYears(LocalDate.now().getYear() - 11); // double price =
 * carsService.calculatePrice(car); // // assertEquals(20000* 0.8, price); // //
 * }
 
 
  @Test public void testInvalidCarChassi() { // Exception exception =
 * ==assertThrows(IllegalArgumentException.class, () ->
 * carService.validateCar(invalidCarChassi)); //
 * assertEquals("Número de chassi inválido", exception.getMessage()); }
 * 
 * }
 */