package com.vodafone.service;

import com.vodafone.model.Customer;
import com.vodafone.model.Email;
import com.vodafone.model.EmailType;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

@Service
public class SendEmailService {
    static String from = "t.m.n.t.ecommerce";

    //generate vrification code
    public String getRandom() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    //send email to the user email
    public boolean sendEmail(Customer user, EmailType emailType, HttpSession httpSession) {
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
            Email emailObj = new Email();
            switch (emailType) {
                case ACTIVATION:
                    emailObj = sendActivationEmail(user.getEmail(), (String) httpSession.getAttribute("verificationCode"));
                    break;
                case FORGET_PASSWORD:
                    emailObj = requestResetPassword(user.getEmail());
                    break;

                case SET_ADMIN_PASSWORD:
                    //todo: save generated password in session and fetch it here
//                    emailObj = sendAdminResetMail(user.getEmail(),);
                    break;
            }
            // creates a new e-mail message
            Message msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(emailObj.getFrom()));
            InternetAddress[] toAddresses = {new InternetAddress(emailObj.getTo())};
            msg.setRecipients(Message.RecipientType.TO, toAddresses);
            msg.setSubject(emailObj.getSubject());
            msg.setSentDate(new Date());
            // set plain text message
            msg.setContent(emailObj.getBody(), "text/html");

            // sends the e-mail
            Transport.send(msg);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }

    public Email requestResetPassword(String email) {
        Email emailObj = new Email();
        emailObj.setSubject("Password reset");
        emailObj.setTo(email);
        emailObj.setFrom(from);
        //todo: test email links
        emailObj.setBody("Dear customer," +
                "\nForget your password?" +
                "\nWe received a request to reset your password." +
                "\nClick on below link to redirect you to reset password page." +
                "\n  http://localhost:8080/Ecommerce_war/webapp/WEB_INF/pages/resetPassword.htm");
        return emailObj;
    }

    public Email sendActivationEmail(String email, String OTP) {
        Email emailObj = new Email();
        emailObj.setSubject("Activate your email");
        emailObj.setTo(email);
        emailObj.setFrom(from);
        //todo: test email links
        emailObj.setBody("Dear customer," +
                "\nWe are happy that you decided to use our service." +
                "\nYou could use below code to verify your account." +
                "\n" + OTP +
                "\nClick on below link to redirect to verification page." +
                "\n http://localhost:8080/Ecommerce_war/webapp/WEB_INF/pages/verify.htm");
        return emailObj;
    }

    public Email sendAdminResetMail(String email, String password) {
        Email emailObj = new Email();
        emailObj.setSubject("Activate your email");
        emailObj.setTo(email);
        //todo: add button to email body
        emailObj.setFrom(from);
        //todo: test email links
        emailObj.setBody("Welcome to Admins' family" +
                "\nWe have created your account. and you can use below password for first login:" +
                "\n " + password +
                "\nFollow this link to create new password http://localhost:8080/Ecommerce_war/webapp/WEB_INF/pages/admin/setAdminPassword.htm" +
                "\nRegards," +
                "\nTMNT super admin.");
        return emailObj;
    }

}
