package com.odde.mailsender.service;

public class PreviewBuilder {
    PreviewRequest preview;

    public PreviewBuilder(PreviewRequest previewRequest){
        preview = previewRequest;
    }

    public static PreviewBuilder validPreview(){
        return new PreviewBuilder(new PreviewRequest("eventspark@gmx.com", "subject", "some body."));
    }

    public PreviewRequest build() {
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
