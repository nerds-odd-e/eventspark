package com.odde.mailsender.bean;

import com.odde.mailsender.data.Event;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
@Builder
public class UserEventListBean {

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
