package com.odde.mailsender.service;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

import static com.icegreen.greenmail.util.ServerSetup.PROTOCOL_SMTP;
import static com.odde.mailsender.service.MailBuilder.validMail;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class MailServiceImplTest {

    @Autowired
    private MailService service;

    private GreenMail greenMail;

    @Before
    public void before() {
        greenMail = new GreenMail(new ServerSetup(2500, "localhost", PROTOCOL_SMTP)).withConfiguration(new GreenMailConfiguration().withDisabledAuthentication());
        greenMail.start();
    }

    @After
    public void after() {
        greenMail.stop();
    }

    @Test
    public void sendMultiple() throws Exception {
        List<MailInfo> mailInfoList = new ArrayList<>();
        mailInfoList.add(validMail().withTo("first@gmail.com").build());
        mailInfoList.add(validMail().withTo("second@gmail.com").build());
        service.sendMultiple(mailInfoList);

        MimeMessage[] messages = greenMail.getReceivedMessages();
        assertThat(messages.length, is(2));

        MimeMessage messageOne = messages[0];
        assertThat(((InternetAddress) messageOne.getRecipients(Message.RecipientType.TO)[0]).getAddress(), is(mailInfoList.get(0).getTo()));
        assertThat(((InternetAddress) messageOne.getFrom()[0]).getAddress(), is(mailInfoList.get(0).getFrom()));
        assertThat(messageOne.getSubject(), is(mailInfoList.get(0).getSubject()));
        assertThat(messageOne.getContent().toString().trim(), is(mailInfoList.get(0).getBody()));

        MimeMessage messageTwo = messages[1];
        assertThat(((InternetAddress) messageTwo.getFrom()[0]).getAddress(), is(mailInfoList.get(1).getFrom()));
        assertThat(((InternetAddress) messageTwo.getRecipients(Message.RecipientType.TO)[0]).getAddress(), is(mailInfoList.get(1).getTo()));
        assertThat(messageTwo.getSubject(), is(mailInfoList.get(1).getSubject()));
        assertThat(messageTwo.getContent().toString().trim(), is(mailInfoList.get(1).getBody()));
    }

    @Test
    public void getTemplate() {
        MailTemplate expected = new MailTemplate(
                "hello $name",
                "hello $name, this is the fixed template."
        );

        assertThat(service.getTemplate(), is(expected));
    }
}