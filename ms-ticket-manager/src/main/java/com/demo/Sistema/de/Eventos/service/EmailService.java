package com.demo.Sistema.de.Eventos.service;

import com.demo.Sistema.de.Eventos.entities.Email;
import com.demo.Sistema.de.Eventos.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {

    @Autowired
    EmailRepository emailRepository;
    @Autowired
    private JavaMailSender emailSender;


    public void sendEmail(Email email) {
        email.setCreatedAt(LocalDateTime.now());
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(email.getEmailFrom());
            message.setTo(email.getEmailTo());
            message.setSubject(email.getSubject());
            message.setText(email.getBody());
            emailSender.send(message);

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            emailRepository.save(email);
        }
    }


}
