package com.odde.mailsender.service;

import java.util.Objects;

public class MailInfo {
    private String from;
    private String to;
    private String subject;
    private String body;

    public MailInfo() {
    }

    public MailInfo(String from, String to, String subject, String body) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MailInfo mailInfo = (MailInfo) o;
        return Objects.equals(from, mailInfo.from) &&
                Objects.equals(to, mailInfo.to) &&
                Objects.equals(subject, mailInfo.subject) &&
                Objects.equals(body, mailInfo.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, subject, body);
    }
}
