package com.rosettastone.succor.backgroundtask.impl;

import java.io.InputStream;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

public class MockEmailSender implements JavaMailSender {

    @Override
    public void send(SimpleMailMessage simpleMessage) {
    }

    @Override
    public void send(SimpleMailMessage[] simpleMessages) {
    }

    @Override
    public MimeMessage createMimeMessage() {
        return null;
    }

    @Override
    public MimeMessage createMimeMessage(InputStream contentStream) {
        return null;
    }

    @Override
    public void send(MimeMessage mimeMessage) {
    }

    @Override
    public void send(MimeMessage[] mimeMessages) {
    }

    @Override
    public void send(MimeMessagePreparator mimeMessagePreparator) {
    }

    @Override
    public void send(MimeMessagePreparator[] mimeMessagePreparators) {
    }
}
