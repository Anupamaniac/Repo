package com.rosettastone.succor.service;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test
public class MailSenderTest {

    public static final int SMTP_PORT = 25;

    private JavaMailSenderImpl mailSender;

    @BeforeClass
    public void init() {
        mailSender = new JavaMailSenderImpl();
        mailSender.setHost("augustine.lan.flt");
        mailSender.setPort(SMTP_PORT);
        mailSender.setDefaultEncoding("UTF-8");
    }

    @Test(expectedExceptions = MailSendException.class, enabled = false)
    public void testMailSendException() throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo("ita@domain.that.not.exists");
        helper.setText("Test email, do not reply");
        helper.setSubject("");
        helper.setFrom("customersuccess@rosettastone.com");
        mailSender.send(message);
    }

    @Test(expectedExceptions = AddressException.class)
    public void testAddressException() throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo("wrong email");
        helper.setText("Test email, do not reply");
        helper.setSubject("");
        helper.setFrom("customersuccess@rosettastone.com");
        mailSender.send(message);
    }
}
