package com.example.carshowroom.Services;

import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "/application-test.properties")
class MailServiceTest
{
    @Autowired
    private MailService mailService;

    @Test
    void should_send_mail() throws Exception
    {
        //given
        String addressee = "adsa2222@interia.pl";
        String sender = "adsa2233@interia.pl";
        String subject = "Tytul";
        String content = "Jakis content";
        //then
        Assertions.assertDoesNotThrow(() -> { mailService.sendMail(addressee, sender, subject, content, false);});
    }

}