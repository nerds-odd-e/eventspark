package com.odde.mailsender.form;

import com.odde.mailsender.data.Event;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class AddEventForm {
    @NotEmpty(message = "must be not empty")
    private String name;

    @NotEmpty(message = "must be not empty")
    private String location;

    @NotEmpty(message = "must be not empty")
    private String summary;

    @NotEmpty(message = "must be not empty")
    private String owner;

    @NotEmpty(message = "must be not empty")
    private String detail;

    @DateTimeFormat(pattern = "uuuu-MM-dd HH:mm")
    private LocalDateTime startDateTime;

    @DateTimeFormat(pattern = "uuuu-MM-dd HH:mm")
    private LocalDateTime endDateTime;

    @NotEmpty(message = "must be not empty")
    private String imagePath;

    public Event createEvent() {
        return Event.builder()
                .name(name)
                .location(location)
                .summary(summary)
                .owner(owner)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .detail(detail)
                .imagePath(imagePath)
                .build();
    }
}
