package com.carrental.carrental.scheduler;

import com.carrental.carrental.entities.BookedCars;
import com.carrental.carrental.entities.Car;
import com.carrental.carrental.repositories.CarBookingRepositories;
import com.carrental.carrental.repositories.CarRepositories;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class SchedularTask {

    private final CarRepositories carRepositories;

    private final CarBookingRepositories carBookingRepositories;

    public SchedularTask(CarRepositories carRepositories, CarBookingRepositories carBookingRepositories) {
        this.carRepositories = carRepositories;
        this.carBookingRepositories = carBookingRepositories;
    }

    @Scheduled(fixedRate = 5000)
    public void scheduledTask(){
       List<BookedCars> carsList = carBookingRepositories.findAll();
       carsList.stream().forEach(car->{
           LocalDate toDate = LocalDate.parse(car.getToDate());
           LocalDate currentDate = LocalDate.now();
           if(currentDate.isAfter(toDate)){
               Car existingCar = carRepositories.findById(car.getCar().getCarId()).get();
               existingCar.setBookedStatus(false);
               carRepositories.save(existingCar);
           }
       });
    }
}
