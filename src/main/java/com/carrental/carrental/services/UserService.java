package com.carrental.carrental.services;

import com.carrental.carrental.entities.User;
import com.carrental.carrental.exception.EmailException;
import com.carrental.carrental.exception.LoginPasswordException;
import com.carrental.carrental.exception.PasswordException;
import com.carrental.carrental.repositories.UserRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;

    private final UserRepositories userRepositories;

    public UserService(PasswordEncoder passwordEncoder, UserRepositories userRepositories) {
        this.passwordEncoder = passwordEncoder;
        this.userRepositories = userRepositories;
    }

    public User findUserByEmail(String email) {
        return userRepositories.findByUserEmail(email);
    }

    public void saveUserRegisteration(String name, String email, String password, String confirmPassword) throws PasswordException {
        if(!passwordVerify(password,confirmPassword)){
            throw new PasswordException("password do not match plzz enter correct password");
        }
        User user = User.builder()
                .userId(UUID.randomUUID())
                .name(name)
                .userEmail(email)
                .password(passwordEncoder.encode(password)).build();
        userRepositories.save(user);
    }

    private boolean passwordVerify(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    public void validLogin(String email, String password) throws EmailException, LoginPasswordException {
        if(!emailValidation(email)){
            throw new EmailException("This  email id is not registered");
        }
        if (!passwordValidation(password,email)){
            throw new LoginPasswordException("enter a valid password");
        }
    }

    private boolean passwordValidation(String password, String email) {
        User user = userRepositories.findByUserEmail(email);
        return user.getPassword().equals(password);
    }

    private boolean emailValidation(String email) {
        User user = userRepositories.findByUserEmail(email);
        return user!=null;
    }

    public String findName(String name) {
        return userRepositories.findByUserEmail(name).getName();
    }

    public void changeUserPassword(String email, String changePassword) {
        User user = userRepositories.findByUserEmail(email);
        user.setPassword(passwordEncoder.encode(changePassword));
        userRepositories.save(user);
    }
}
