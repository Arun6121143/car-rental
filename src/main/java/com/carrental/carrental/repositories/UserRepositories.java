package com.carrental.carrental.repositories;

import com.carrental.carrental.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepositories extends JpaRepository<User, UUID> {
    User findByUserEmail(String email);
}
