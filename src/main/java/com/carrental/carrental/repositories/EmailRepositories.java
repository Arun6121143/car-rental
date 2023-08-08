package com.carrental.carrental.repositories;

import com.carrental.carrental.entities.Car;
import com.carrental.carrental.entities.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmailRepositories extends JpaRepository<Email, UUID> {

}