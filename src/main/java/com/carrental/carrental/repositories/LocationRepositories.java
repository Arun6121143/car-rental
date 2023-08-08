package com.carrental.carrental.repositories;

import com.carrental.carrental.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LocationRepositories extends JpaRepository<Location,Integer> {
    Location findByLocationName(String location);
}