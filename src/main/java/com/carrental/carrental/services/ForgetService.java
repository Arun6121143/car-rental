package com.carrental.carrental.services;

import com.carrental.carrental.entities.User;
import com.carrental.carrental.repositories.UserRepositories;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class ForgetService {
    private final UserRepositories userRepositories;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private  String sender;

    public ForgetService(UserRepositories userRepositories, JavaMailSender javaMailSender) {
        this.userRepositories = userRepositories;
        this.javaMailSender = javaMailSender;
    }

    public boolean sendOtp(String email, HttpSession session, Model model) {
        User user = userRepositories.findByUserEmail(email);
        if(user==null){
           return false;
        }
       return true;
    }

    @Async
    public void sendOtpMail(String email, int otp) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("Your OTP for password chnage");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setText("your OTP is "+ otp);
        javaMailSender.send(simpleMailMessage);
    }
}