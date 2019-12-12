package com.odde.mailsender.bean;

import com.odde.mailsender.data.Event;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Value
@Builder
public class UserEventListBean {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    @Value
    @Builder
    public static class EventBean {

        private String startDateTime;
        private String title;
        private String location;
        private String summary;
        private String ticketStatus;

        public static EventBean of(Event entity) {
            return EventBean.builder()
                    .title(entity.getName())
                    .location(entity.getLocation())
                    .summary(entity.getSummary())
                    .startDateTime(FORMATTER.format(entity.getStartDateTime()))
                    .ticketStatus("残りわずか")
                    .build();
        }
    }

    @Singular("addEvent")
    private List<EventBean> eventList;

    public static UserEventListBean create(List<Event> entityList) {
        return UserEventListBean.builder()
                .eventList(entityList.stream()
                        .map(EventBean::of)
                        .collect(Collectors.toList()))
                .build();
    }
}
