package com.carrental.carrental.Controller;

import com.carrental.carrental.services.ForgetService;
import com.carrental.carrental.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;

@Controller
public class ForgetPasswordController {
    private final ForgetService forgetService;

    private final UserService userService;

    public ForgetPasswordController(ForgetService forgetService, UserService userService) {
        this.forgetService = forgetService;
        this.userService = userService;
    }

    @GetMapping("/forgotPasswordPage")
    public String forgerPasswordPage(Model model){
        model.addAttribute("messages","Forgot Password");
        return "forgetpassword";
    }
    @GetMapping("/otpPage")
    public String otpPage(){
       return "verifyotp";
    }

    @PostMapping("/sendOtp")
    public String sendOtp(@RequestParam(value = "email") String email, HttpSession session, Model model){
      boolean isValid = forgetService.sendOtp(email,session,model);
      if(!isValid){
          model.addAttribute("message","This not a registered email id");
          return "forgetpassword";
      }
        Random random = new Random();
        int otp = random.nextInt(1000,9999);
        forgetService.sendOtpMail(email,otp);
        session.setAttribute("email",email);
        session.setAttribute("myotp",otp);
        return "verifyotp";
    }

    @GetMapping("/verify")
    public String verifyOtp(@RequestParam(value = "otp") int otp,HttpSession session,Model model){
        int myOtp =(int) session.getAttribute("myotp");
        String email = (String) session.getAttribute("email");
        if(otp!=myOtp){
            model.addAttribute("message","Enter Correct OTP");
            return "verifyotp";
        }
        session.setAttribute("email",email);
        return "redirect:/showChangePasswordPage";
    }

    @GetMapping("/showChangePasswordPage")
    public String showPasswordPage(){
        return "changepassword";
    }
    @PostMapping("/changePassword")
    public String chnageUserPassword(@RequestParam(value = "changePassword") String changePassword,HttpSession session){
        String email = (String) session.getAttribute("email");
        userService.changeUserPassword(email,changePassword);
        return "redirect:/loginPage";
    }

    @GetMapping("/changePassword")
    public String  changePassword(Model model){
        model.addAttribute("messages","Change Password");
        return "forgetpassword";
    }
}