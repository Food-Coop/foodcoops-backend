package de.dhbw.foodcoop.warehouse.plugins.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("foodcoop@orat.de"); // Optional, je nach Servereinstellungen
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
    
    public void sendEmailWithPDF(String to, String subject, String text, byte[] pdfBytes, String pdfFileName) throws MessagingException {
    	MimeMessage message = mailSender.createMimeMessage();
    	MimeMessageHelper helper = new MimeMessageHelper(message, true);
    	
    	helper.setFrom("foodcoop@orat.de"); // Optional, je nach Servereinstellungen
    	helper.setTo(to);
    	helper.setSubject(subject);
    	helper.setText(text);
    	
        ByteArrayResource pdfResource = new ByteArrayResource(pdfBytes);
        helper.addAttachment(pdfFileName, pdfResource);
        mailSender.send(message);
    }
}