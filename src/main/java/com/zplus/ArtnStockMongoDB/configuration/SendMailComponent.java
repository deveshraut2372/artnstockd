package com.zplus.ArtnStockMongoDB.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class SendMailComponent {

    @Autowired
    private JavaMailSender sender;

    public String sendMail(String toEmailId, String content, String subject) throws MessagingException {


//        final String userName = "contact@auctionbanks.com";
//        final String password ="auctionbanks@123";
        final String userName = "apurv.patole@zpluscybertech.com";
        final String password = "Apurv@123";
        String fromEmail = "apurv.patole@zpluscybertech.com";
        String toEmail = toEmailId;

        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "webmail.shreerajdeveloper.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });

        MimeMessage msg = new MimeMessage(session);
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        try {
            helper.setFrom(new InternetAddress(fromEmail));
            // helper.addRecipient(Message.RecipientType.TO,new InternetAddress(toEmail));
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(content, true);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        sender.send(msg);
        return "Mail Sent Success!";
    }
}
