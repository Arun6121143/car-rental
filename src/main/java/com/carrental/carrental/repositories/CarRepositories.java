package com.carrental.carrental.repositories;

import com.carrental.carrental.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CarRepositories  extends JpaRepository<Car, UUID> {
    Car findByCarName(String car);
}