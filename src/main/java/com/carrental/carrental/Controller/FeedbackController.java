package com.carrental.carrental.Controller;

import com.carrental.carrental.services.FeedbackService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;
import java.util.UUID;

@Controller
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping("/feedbackCar")
    public String feedback(@RequestParam(value = "carId") UUID id,
                           Principal principal,
                           @RequestParam(value = "feedback") String feedback){
        System.out.println(id);
         feedbackService.saveFeedback(id,principal,feedback);
         return "redirect:/home";
    }
}