package com.carrental.carrental.Controller;

import com.carrental.carrental.exception.EmailException;
import com.carrental.carrental.exception.LoginPasswordException;
import com.carrental.carrental.exception.PasswordException;
import com.carrental.carrental.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/registration")
    public String userRegistration(@RequestParam(value = "name") String name,
                                   @RequestParam(value = "email") String email,
                                   @RequestParam(value = "password") String password,
                                   @RequestParam(value = "confirmPassword") String confirmPassword) throws PasswordException {
        userService.saveUserRegisteration(name,email,password,confirmPassword);
        return "redirect:/loginPage";
    }

    @GetMapping("/login")
    public String loginDetails(@RequestParam(value = "email") String email,
                               @RequestParam(value = "password") String password) throws  EmailException, LoginPasswordException {
        userService.validLogin(email,password);
        return "redirect:/";
    }
}