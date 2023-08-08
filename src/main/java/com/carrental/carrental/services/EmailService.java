package com.carrental.carrental.services;

import com.carrental.carrental.entities.BookedCars;
import com.carrental.carrental.entities.Car;
import com.carrental.carrental.entities.Email;
import com.carrental.carrental.entities.Location;
import com.carrental.carrental.repositories.EmailRepositories;
import com.lowagie.text.DocumentException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class EmailService {
    @Value("${spring.mail.username}")
    private  String sender;

    private final String SUBJECT="car rental details";

    private final PdfService pdfService;

    private final EmailRepositories emailRepositories;
    private final JavaMailSender javaMailSender;

    public EmailService(PdfService pdfService, EmailRepositories emailRepositories, JavaMailSender javaMailSender) {
        this.pdfService = pdfService;
        this.emailRepositories = emailRepositories;
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void saveEmail(String receiverMail, Car existingCar, String fromDate, String toDate, Location savedLocation, String name, BookedCars bookedCars) throws DocumentException, IOException, MessagingException {
       Email email = Email.builder()
               .sender(sender)
               .receiver(receiverMail)
               .message("carModel: " + existingCar.getCarName() + "\n" +
                "fromDate: " + fromDate + "\n" +
                "toDate: " + toDate + "\n" +
                "locationName: " + savedLocation.getLocationName())
               .status(true)
               .build();
       emailRepositories.save(email);
       sendEmailPdf(email,existingCar,savedLocation,fromDate,toDate,name,bookedCars.getPrice());
    }

    private void sendEmailPdf(Email email, Car existingCar, Location locationName, String fromDate, String toDate, String name, Integer price) throws DocumentException, IOException, MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(sender);
        helper.setTo(email.getReceiver());
        helper.setText(" ");
        helper.setSubject(SUBJECT);
        File pdfFile = pdfService.createPdfHtml(existingCar.getCarImageUrl(),existingCar.getCarName(),locationName,fromDate,toDate,name,price);
        FileSystemResource fileSystemResource = new FileSystemResource(pdfFile);
        helper.addAttachment(fileSystemResource.getFilename(),fileSystemResource);
        javaMailSender.send(message);
    }
}