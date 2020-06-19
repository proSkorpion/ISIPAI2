package com.example.carshowroom.Services;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService
{
    private JavaMailSender javaMailSender;

    public MailService(JavaMailSender javaMailSender)
    {
        this.javaMailSender = javaMailSender;
    }


    public void sendMail(String addressee, String subject, String content, boolean isHtmlContent) throws MessagingException
    {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(addressee);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(content);
        javaMailSender.send(mimeMessage);
    }

    public void sendMail(String addressee, String sender, String subject, String content, boolean isHtmlContent) throws MessagingException
    {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(addressee);
        mimeMessageHelper.setSubject(subject + " uzytkownik: "+sender);
        mimeMessageHelper.setText(content);
        javaMailSender.send(mimeMessage);
    }
}
