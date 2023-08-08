package com.carrental.carrental.repositories;

import com.carrental.carrental.entities.BookedCars;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CarBookingRepositories extends JpaRepository<BookedCars, UUID> {

    @Query(value = "SELECT * FROM booked_cars  WHERE user_id = :userId",nativeQuery = true)
    List<BookedCars> findBookedCarsUserId(UUID userId);
}