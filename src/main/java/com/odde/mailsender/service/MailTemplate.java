package com.odde.mailsender.service;

public class MailTemplate {
    private String subject;
    private String body;

    public MailTemplate(String subject, String body) {
        this.subject = subject;
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }
}
