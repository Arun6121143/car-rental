package com.carrental.carrental.Controller;

import com.carrental.carrental.entities.BookedCars;
import com.carrental.carrental.entities.User;
import com.carrental.carrental.services.BookedCarsService;
import com.carrental.carrental.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class BookedCarController {

    private final BookedCarsService bookedCarsService;

    private final UserService userService;

    public BookedCarController(BookedCarsService bookedCarsService, UserService userService) {
        this.bookedCarsService = bookedCarsService;
        this.userService = userService;
    }

    @GetMapping("/getUserBookingDetails")
    public String bookedCarDetails(Principal principal, Model model){
         User user = userService.findUserByEmail(principal.getName()) ;
         List<BookedCars> bookedCarsList = bookedCarsService.findBookedCarsByUserId(user.getUserId());
         model.addAttribute("bookedCars",bookedCarsList);
         model.addAttribute("bookedListSize",bookedCarsList.size());
         return "bookingDetails";
    }
}