package com.carrental.carrental.exceptionhandler;

import com.carrental.carrental.exception.EmailException;
import com.carrental.carrental.exception.LoginPasswordException;
import com.carrental.carrental.exception.PasswordException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(PasswordException.class)
    public String passwordExceptionHandler(PasswordException passwordException, Model model){
        model.addAttribute("message",passwordException.getMessage());
        return "register";
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(EmailException.class)
    public String emailExceptionHandler(EmailException emailException, Model model){
        model.addAttribute("message",emailException.getMessage());
        return "login";
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(LoginPasswordException.class)
    public String emailExceptionHandler(LoginPasswordException loginPasswordException, Model model){
        model.addAttribute("message",loginPasswordException.getMessage());
        return "login";
    }
}