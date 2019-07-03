package com.odde.mailsender.service;

import java.util.Objects;

public class MailTemplate {
    public static final String TEMPLATE_SUBJECT = "hello $name";
    public static final String TEMPLATE_BODY = "hello $name, this is the fixed template.";
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MailTemplate that = (MailTemplate) o;
        return Objects.equals(subject, that.subject) &&
                Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, body);
    }
}
