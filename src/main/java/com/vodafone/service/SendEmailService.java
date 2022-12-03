package com.vodafone.service;

import com.vodafone.model.Customer;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

@Service
public class SendEmailService {
    //generate vrification code
    public String getRandom() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    //send email to the user email
    public boolean sendEmail(Customer user) {
        try {
            String userName = "t.m.n.t.ecommerce@gmail.com";
            String password = "ekuivkoxncvvndgb";
            // sets SMTP server properties
            Properties properties = new Properties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
            properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            properties.setProperty("mail.transport.protocol", "smtp");

            // creates a new session with an authenticator
            Authenticator auth = new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(userName, password);
                }
            };

            Session session = Session.getInstance(properties, auth);

            // creates a new e-mail message
            Message msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(userName));
            InternetAddress[] toAddresses = {new InternetAddress(user.getEmail())};
            msg.setRecipients(Message.RecipientType.TO, toAddresses);
            msg.setSubject("User Email Verification");
            msg.setSentDate(new Date());
            // set plain text message
            msg.setContent("Registered successfully.Please verify your account using this code: " + user.getCode(), "text/html");

            // sends the e-mail
            Transport.send(msg);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }

}
