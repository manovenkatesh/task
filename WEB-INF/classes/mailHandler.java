package com.zoho;

import java.util.Random;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class mailHandler {

    public int sendmail(String mailid) {
		int otp;
        final String username = mailid;
        final String password = "29march1997";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("manovenkateshmds@gmail.com", password);
            }
          });

        try {
			
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("manovenkateshmds@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(username));
            message.setSubject("OTP for reset password");
			Random rand=new Random();
			 otp=rand.nextInt(100000);
            message.setText("Dear user,"
                + "\n\n Your temproary OTP is: "+ otp);

            Transport.send(message);

            System.out.println("Done");


        } catch (MessagingException e) {
			System.out.println(""+e);
            throw new RuntimeException(e);
        }
		return otp;
    }
}