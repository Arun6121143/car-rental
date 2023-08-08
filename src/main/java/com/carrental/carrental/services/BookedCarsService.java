package com.carrental.carrental.services;

import com.carrental.carrental.entities.BookedCars;
import com.carrental.carrental.entities.Car;
import com.carrental.carrental.entities.Location;
import com.carrental.carrental.entities.User;
import com.carrental.carrental.repositories.CarBookingRepositories;
import com.carrental.carrental.repositories.CarRepositories;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;

@Service
public class BookedCarsService {
    private final CarBookingRepositories carBookingRepositories;

    private final CarRepositories carRepositories;

    public BookedCarsService(CarBookingRepositories carBookingRepositories, CarRepositories carRepositories) {
        this.carBookingRepositories = carBookingRepositories;
        this.carRepositories = carRepositories;
    }
    public BookedCars savebookedCars(User savedUser, Location savedLocation, Car existingCar, String toDate, String fromDate) {
        int price = calculatePrice(fromDate,toDate)* existingCar.getCarRentalPrice();
        existingCar.setBookedStatus(true);
        carRepositories.save(existingCar);
        BookedCars bookedCars = BookedCars.builder()
                .car(existingCar)
                .user(savedUser)
                .location(savedLocation)
                .fromDate(fromDate)
                .toDate(toDate)
                .price(price).build();
      return   carBookingRepositories.save(bookedCars);
    }

    public int calculatePrice(String fromDate, String toDate) {
        LocalDate date1 = LocalDate.parse(fromDate);
        LocalDate date2 = LocalDate.parse(toDate);
        Period intervalPeriod = Period.between(date1,date2);
        return intervalPeriod.getDays();
    }

    public List<BookedCars> findBookedCarsByUserId(UUID userId) {
        return carBookingRepositories.findBookedCarsUserId(userId);
    }
}
