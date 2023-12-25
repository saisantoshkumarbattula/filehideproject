package org.filehide.service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SignUpOrLoginStatusMail {
    public static void sendOTP(String email, String signUpOrLogin, String status) {
        // Recipient's email ID needs to be mentioned.
        String to = email;
        // Sender's email ID needs to be mentioned
        String from = "hack3r.1702@gmail.com";
        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";
        // Get system properties
        Properties properties = System.getProperties();
        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "exyn wtoi hnfc mcsq");
            }

        });
        // Used to debug SMTP issues
        session.setDebug(true);
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));
            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // Set Subject: header field
            message.setSubject(signUpOrLogin + " status");
            // Now set the actual message
            message.setText(status.equals("successful") ? ("Your " + signUpOrLogin + " was " + status ) : ("your " + signUpOrLogin + " " + status));
            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
//            mex.printStackTrace();
            System.out.println(mex.getMessage());
        }
    }
}
