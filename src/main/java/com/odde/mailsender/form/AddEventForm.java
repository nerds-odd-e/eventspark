package com.odde.mailsender.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
public class AddEventForm {
    @NotEmpty(message = "must be not empty")
    private String name;

    @NotEmpty(message = "must be not empty")
    private String location;

    @NotEmpty(message = "must be not empty")
    private String summary;

    @NotEmpty(message = "must be not empty")
    private String detailText;

    @DateTimeFormat(pattern = "uuuu-MM-dd HH:mm")
    private LocalDateTime startDateTime;

    @DateTimeFormat(pattern = "uuuu-MM-dd HH:mm")
    private LocalDateTime endDateTime;



}
