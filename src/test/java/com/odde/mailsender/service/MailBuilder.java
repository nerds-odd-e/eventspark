package com.odde.mailsender.service;

public class MailBuilder {
    MailInfo mail;

    public MailBuilder(MailInfo mailInfo){
        mail = mailInfo;
    }

    public static MailBuilder validMail(){
        return new MailBuilder(new MailInfo("eventspark@gmx.com", "eventspark@gmx.com", "subject", "some body."));
    }

    public MailInfo build() {
        return mail;
    }

    public MailBuilder withFrom(String from) {
        mail.setFrom(from);
        return this;
    }

    public MailBuilder withTo(String to) {
        mail.setTo(to);
        return this;
    }

    public MailBuilder withSubject(String subject) {
        mail.setSubject(subject);
        return this;
    }

    public MailBuilder withBody(String body) {
        mail.setBody(body);
        return this;
    }
}
