package com.carrental.carrental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableAutoConfiguration
@EnableScheduling
public class CarRentalApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarRentalApplication.class, args);
	}

//	@Autowired
//	CarRepositories carRepositories;
//	@PostConstruct
//	public void  addCarData(){
//		Car car = new Car();
//		car.setCarName("convertible");
//		car.setBookedStatus(false);
//		car.setCarImageUrl("https://imgd.aeplcdn.com/600x337/n/cw/ec/149075/z4-exterior-right-front-three-quarter.jpeg?isig=0&q=75");
//		carRepositories.save(car);
//	}

}
