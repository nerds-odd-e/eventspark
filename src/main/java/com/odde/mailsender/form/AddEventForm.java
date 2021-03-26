package com.odde.mailsender.form;

import com.odde.mailsender.data.Event;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class AddEventForm {
    @NotEmpty(message = "Name must not be empty")
    private String name;

    @NotEmpty(message = "Location must not be empty")
    private String location;

    @NotEmpty(message = "Summary must not be empty")
    private String summary;

    @NotEmpty(message = "Owner must not be empty")
    private String owner;

    @NotEmpty(message = "Detail must not be empty")
    private String detail;

    @NotNull(message = "Start date time must not be empty")
    @DateTimeFormat(pattern = "uuuu-MM-dd HH:mm")
    private LocalDateTime startDateTime;

    @NotNull(message = "End date time must not be empty")
    @DateTimeFormat(pattern = "uuuu-MM-dd HH:mm")
    private LocalDateTime endDateTime;

    @NotEmpty(message = "Image url must not be empty")
    private String imagePath;

    public String startDateTimeToString() {
        if (startDateTime == null) {
            return "";
        }
        return startDateTime.format(DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm"));
    }

    public String endDateTimeToString() {
        if (endDateTime == null) {
            return "";
        }
        return endDateTime.format(DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm"));
    }

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
