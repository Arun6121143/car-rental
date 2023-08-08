package com.carrental.carrental.services;

import com.carrental.carrental.entities.Car;
import com.carrental.carrental.repositories.CarRepositories;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class CarService {
    private final CarRepositories carRepositories;

    public CarService(CarRepositories carRepositories) {
        this.carRepositories = carRepositories;
    }


    public List<Car> findAllCars() {
        return carRepositories.findAll();
    }
    public Car findCarById(String carId) {
        return carRepositories.findByCarName(carId);
    }
}
