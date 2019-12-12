package com.odde.mailsender.bean;

import com.odde.mailsender.data.Event;
import com.odde.mailsender.data.RegistrationInfo;
import com.odde.mailsender.data.Ticket;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Value
@Builder
public class UserEventListBean {

    public static final String TICKET_COUNT_FEW = "残りわずか";
    public static final String TICKET_COUNT_LOT = "申し込み受付中";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    @Value
    @Builder
    public static class EventBean {

        private String startDateTime;
        private String title;
        private String location;
        private String summary;
        private String ticketStatus;

        public static EventBean of(Event event, List<Ticket> ticketList, List<RegistrationInfo> registrationInfoList) {
            long total = ticketList.stream().mapToLong(ticket -> ticket.getTicketTotal()).sum();
            long registerTotal = registrationInfoList.stream().mapToLong(registrationInfo -> registrationInfo.getTicketCount()).sum();
            String ticketStatus;
            if ((double) registerTotal / total > 0.8) {
                ticketStatus = TICKET_COUNT_FEW;
            } else {
                ticketStatus = TICKET_COUNT_LOT;
            }
            return EventBean.builder()
                    .title(event.getName())
                    .location(event.getLocation())
                    .summary(event.getSummary())
                    .startDateTime(FORMATTER.format(event.getStartDateTime()))
                    .ticketStatus(ticketStatus)
                    .build();
        }
    }

    @Singular("addEvent")
    private List<EventBean> eventList;

    public static UserEventListBean create(List<Event> eventList, List<Ticket> ticketList, List<RegistrationInfo> registrationInfoList) {
        return UserEventListBean.builder()
                .eventList(eventList.stream()
                        .map(event -> {
                            List<Ticket> filteredTicketList = ticketList.stream()
                                    .filter(ticket -> ticket.getEventId().equals(event.getId()))
                                    .collect(Collectors.toList());
                            List<RegistrationInfo> filteredRegistrationInfoList = registrationInfoList.stream()
                                    .filter(registrationInfo -> registrationInfo.getEventId().equals(event.getId()))
                                    .collect(Collectors.toList());
                            return EventBean.of(event, filteredTicketList, filteredRegistrationInfoList);
                        })
                        .collect(Collectors.toList()))
                .build();
    }
}
