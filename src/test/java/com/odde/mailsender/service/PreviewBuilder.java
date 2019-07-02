package com.odde.mailsender.service;

public class PreviewBuilder {
    PreviewInfo preview;

    public PreviewBuilder(PreviewInfo previewInfo){
        preview = previewInfo;
    }

    public static PreviewBuilder validPreview(){
        return new PreviewBuilder(new PreviewInfo("eventspark@gmx.com", "subject", "some body."));
    }

    public PreviewInfo build() {
        return preview;
    }

    public PreviewBuilder withTo(String to) {
        preview.setTo(to);
        return this;
    }

    public PreviewBuilder withSubject(String subject) {
        preview.setSubject(subject);
        return this;
    }

    public PreviewBuilder withBody(String body) {
        preview.setBody(body);
        return this;
    }
}
