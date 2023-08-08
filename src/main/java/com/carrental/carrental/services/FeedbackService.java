package com.carrental.carrental.services;

import com.carrental.carrental.entities.Car;
import com.carrental.carrental.entities.Feedback;
import com.carrental.carrental.entities.User;
import com.carrental.carrental.repositories.CarRepositories;
import com.carrental.carrental.repositories.FeedbackRepo;
import com.carrental.carrental.repositories.UserRepositories;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FeedbackService {

    private final CarRepositories carRepositories;

    private final FeedbackRepo feedbackRepo;

    private final UserRepositories userRepositories;

    public FeedbackService(CarRepositories carRepositories, FeedbackRepo feedbackRepo, UserRepositories userRepositories) {
        this.carRepositories = carRepositories;
        this.feedbackRepo = feedbackRepo;
        this.userRepositories = userRepositories;
    }

    public void saveFeedback(UUID id, Principal principal, String feedback) {
        Car existingCar = carRepositories.findById(id).get();
        User user = userRepositories.findByUserEmail(principal.getName());
        List<Feedback> feedbackCarList = existingCar.getFeedbackcarList();
        List<Feedback> feedbackUserList = user.getUserFeedbackList();
        Feedback newFeedback = Feedback.builder()
                .feedback(feedback)
                .car(existingCar)
                .user(user)
                .build();
       Feedback savedFeedback =  feedbackRepo.save(newFeedback);
       feedbackCarList.add(savedFeedback);
       feedbackUserList.add(savedFeedback);
       carRepositories.save(existingCar);
       userRepositories.save(user);
    }
}
