package com.carrental.carrental.repositories;

import com.carrental.carrental.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FeedbackRepo extends JpaRepository<Feedback,UUID> {
}
