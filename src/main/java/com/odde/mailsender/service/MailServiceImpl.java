package com.odde.mailsender.service;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailServiceImpl implements MailService {

    @Value("${smtp.host}")
    private String mailHost;
    @Value("${smtp.port}")
    private Integer smtpPort;
    @Value("${smtp.ssl.enable}")
    private Boolean sslEnable;
    private static final String CHARSET = "ISO-2022-JP";
    private static final String SENDER_NAME = "eventspark@gmx.com";
    private static final String PASSWORD = "scrumdeveloper2019";

    @Override
    public void send(MailInfo mailInfo) throws Exception {
        SimpleEmail simpleEmail = setupEmailEnvironment();

        setMailInfo(mailInfo, simpleEmail);

        try {
            simpleEmail.send();
        } catch (EmailException e) {
            e.printStackTrace();
            throw new Exception("Try to send email, but failed");
        }
    }

    public void sendMultiple(List<MailInfo> mailInfoList) throws Exception {
        for (MailInfo mailInfo : mailInfoList) {
            send(mailInfo);
        }
    }

    private SimpleEmail setupEmailEnvironment() {
        SimpleEmail simpleEmail = new SimpleEmail();
        simpleEmail.setHostName(mailHost);
        if (sslEnable) {
            simpleEmail.setSSLOnConnect(true);
            simpleEmail.setAuthentication(SENDER_NAME, PASSWORD);
        }

        simpleEmail.setSmtpPort(smtpPort);
        simpleEmail.setCharset(CHARSET);
        return simpleEmail;
    }

    private void setMailInfo(MailInfo mailInfo, SimpleEmail simpleEmail) throws EmailException {
        simpleEmail.setFrom(mailInfo.getFrom());
        simpleEmail.setSubject(mailInfo.getSubject());
        simpleEmail.setMsg(mailInfo.getBody());
        simpleEmail.addTo(mailInfo.getTo());
    }
}
