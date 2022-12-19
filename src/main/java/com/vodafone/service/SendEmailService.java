package com.vodafone.service;

import com.vodafone.model.Customer;
import com.vodafone.model.Email;
import com.vodafone.model.EmailType;
import com.vodafone.model.User;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${EMAIL}")
    public String EMAIL;
    @Value("${EMAIL_PASS}")
    public String EMAIL_PASS;
    static String from = "t.m.n.t.ecommerce";
    HashService hashService;

    CustomerService customerService;

    public SendEmailService(HashService hashService, CustomerService customerService) {
        this.hashService = hashService;
        this.customerService = customerService;
    }

    //generate verification code
    public String getRandom() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    //send email to the user email
    public boolean sendEmail(User user, EmailType emailType, HttpSession httpSession) {
        try {
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
                    return new PasswordAuthentication(EMAIL, EMAIL_PASS);
                }
            };

            Session session = Session.getInstance(properties, auth);
            Email emailObj = identifyEmailType(emailType, httpSession);
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

    public Email identifyEmailType(EmailType emailType, HttpSession session) {
        Email emailObj = new Email();
        String subject = "", body = "";
        emailObj.setTo((String) session.getAttribute("email"));
        emailObj.setFrom(from);
        //set subject and body based on EmailType flag
        switch (emailType) {
            case ACTIVATION:
                Customer customer = customerService.getByMail((String) session.getAttribute("email"));
                String otp = customer.getCode();
                if (otp == null) //if otp is expired then generate new one
                {
                    otp = getRandom();
                    customer.setCode(otp);
                    customerService.update(customer.getId(), customer);
                    session.setAttribute("verificationCode", otp);
                }
                subject = "Activate your email";
                body = "Dear customer,\n" +
                        "We are happy that you decided to use our service.\n" +
                        "You could use below code to verify your account.\n" +
                        "" + otp;
                break;
            case FORGET_PASSWORD:
                subject = "Password reset";
                body = "Dear customer,\n" +
                        "Forget your password?\n" +
                        "You are suspended due to exceeding your login attempts [3]\n" +
                        "Your next login will let you reset your password.\n" +
                        "Wish you happy shopping experience.\n" +
                        "Regards,\n" +
                        "TMNT team.";
                break;

            case SET_ADMIN_PASSWORD:
                subject = "Activate your email";
                body = "Welcome to Admins' family\nWe have created your account. and you can use below password for first login:\n "
                        + session.getAttribute("dec_password") +
                        "\nHead to TMNT to set your password" +
                        "\nRegards," +
                        "\nTMNT super admin.";
                break;
        }
        emailObj.setSubject(subject);
        emailObj.setBody(body);
        return emailObj;
    }
}
