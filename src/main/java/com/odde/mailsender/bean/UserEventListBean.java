package com.odde.mailsender.bean;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class UserEventListBean {

    @Value
    @Builder
    public static class Event {

        private String startDateTime;
        private String title;
        private String location;
        private String summary;
        private String ticketStatus;
    }

    @Singular("addEvent")
    private List<Event> eventList;
}
