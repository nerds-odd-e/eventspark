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

    public static final String TICKET_COUNT_FEW = "Few available";
    public static final String TICKET_COUNT_LOT = "Available";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
    private static final Double SOLD_NOTIFICATION_LEVEL = 0.2;

    @Value
    @Builder
    public static class EventBean {

        private String eventId;
        private String startDateTime;
        private String title;
        private String location;
        private String summary;
        private String ticketStatus;
        private String imagePath;

        public static EventBean of(Event event, List<Ticket> ticketList, List<RegistrationInfo> registrationInfoList) {
            long total = ticketList.stream().mapToLong(ticket -> ticket.getTicketTotal()).sum();
            long unsoldTickets = event.countAllUnsoldTickets(ticketList, registrationInfoList);

            return EventBean.builder()
                    .eventId(event.getId())
                    .title(event.getName())
                    .location(event.getLocation())
                    .summary(event.getSummary())
                    .startDateTime(FORMATTER.format(event.getStartDateTime()))
                    .ticketStatus(getTicketStatus(total, unsoldTickets))
                    .imagePath(event.getImagePath())
                    .build();
        }

        private static String getTicketStatus(long total, double unsoldTickets) {
            if (unsoldTickets / total < SOLD_NOTIFICATION_LEVEL) {
                return TICKET_COUNT_FEW;
            } else {
                return TICKET_COUNT_LOT;
            }
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
