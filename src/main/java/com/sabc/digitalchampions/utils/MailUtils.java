package com.sabc.digitalchampions.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class MailUtils {

    @Value("${email.username}")
    private String username;
    @Value("${email.password}")
    private String password;
    @Value("${email.smtp.host}")
    private String host;
    @Value("${email.smtp.port}")
    private String port;

    static Logger log = LogManager.getLogger(MailUtils.class);

    public void sendHTMLMail(String from, Iterable<String> to, String subject, String body){
        System.out.println(username);

        username = "pierremoro107@gmail.com";
        password = "ldyrgcawadqdlmft";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        });
        String strTo = String.join(",",to);
        try{
            System.out.println("Message was sent to "+ strTo);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(strTo));

            message.setSubject(subject);
            message.setContent(body,"text/html; charset=utf-8");

            Transport.send(message);

            log.info("Mail has been sent to "+ strTo);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
