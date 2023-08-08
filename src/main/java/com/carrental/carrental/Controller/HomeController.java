package com.carrental.carrental.Controller;

import com.carrental.carrental.entities.BookedCars;
import com.carrental.carrental.entities.Car;
import com.carrental.carrental.entities.Location;
import com.carrental.carrental.entities.User;
import com.carrental.carrental.services.*;
import com.lowagie.text.DocumentException;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;
@Controller
public class HomeController {
    private final CarService carService;
    private final  UserService userService;
    private final LocationService locationService;
    private final BookedCarsService bookedCarsService;
    private final EmailService emailService;

    public HomeController(CarService carService, UserService userService, LocationService locationService, BookedCarsService bookedCarsService, EmailService emailService) {
        this.carService = carService;
        this.userService = userService;
        this.locationService = locationService;
        this.bookedCarsService = bookedCarsService;
        this.emailService = emailService;
    }

    @GetMapping("/feedback")
    public String showFeedbackPage(Model model){
        List<Car> carList = carService.findAllCars();
        model.addAttribute("carList",carList);
        return "feedback";
    }
    @GetMapping("/home")
    public String showCarRentalPage(Principal principal , Model model){
        List<Car> carList = carService.findAllCars();
        model.addAttribute("carList",carList);
        List<Location> locationList = locationService.findAllLocations();
        model.addAttribute("locationList",locationList);
        model.addAttribute("username",userService.findName(principal.getName()));
        return "home";
    }
    @GetMapping("/loginPage")
    public String viewLoginPage(){
        return "login";
    }

    @GetMapping("/registrationPage")
    public String viewRegisterPage(){
        return "register";
    }
    @PostMapping("/save")
    public String saveDetails(Principal principal,
                              @RequestParam(value = "location") String location,
                              @RequestParam(value = "car") String car,
                              @RequestParam(value = "fromDate") String fromDate,
                              @RequestParam(value = "toDate") String toDate,
                              Model model) throws DocumentException, IOException, MessagingException {
        User savedUser = userService.findUserByEmail(principal.getName());
        Location savedLocation = locationService.findLocationByName(location);
        Car existingCar = carService.findCarById(car);
        BookedCars bookedCars = bookedCarsService.savebookedCars(savedUser,savedLocation,existingCar,toDate,fromDate);
        emailService.saveEmail(principal.getName(),existingCar,fromDate,toDate,savedLocation,savedUser.getName(),bookedCars);
        addUserBookedDetails(model,savedLocation.getLocationName(),savedLocation.getLatitude(),savedLocation.getLongitude(),fromDate,toDate,existingCar.getCarName(),bookedCars.getBookedId());
        return "map";
    }

    private void addUserBookedDetails(Model model,String locationName, String latitude, String longitude, String fromDate, String toDate, String carName, UUID bookedId) {
        model.addAttribute("location",locationName);
        model.addAttribute("latitude",latitude);
        model.addAttribute("longitude",longitude);
        model.addAttribute("carName",carName);
        model.addAttribute("bookingId",bookedId);
        model.addAttribute("fromDate",fromDate);
        model.addAttribute("toDate",toDate);
    }

    @GetMapping("/userProfile")
    public String userProfile(Principal principal,Model model){
        User user = userService.findUserByEmail(principal.getName());
        List<BookedCars> bookedCarsList = bookedCarsService.findBookedCarsByUserId(user.getUserId());
        model.addAttribute("user",user);
        model.addAttribute("CarsBooked","Cars Booked "+bookedCarsList.size());
        return "userProfile";
    }
}