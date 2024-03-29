package com.odde.mailsender.data;

import com.odde.mailsender.service.MailInfo;
import lombok.Builder;
import lombok.Data;

import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Objects;

@Data
@Builder
public class Ticket {

    @Id
    private String id;

    private String ticketName;
    private Long ticketPrice;
    private Long ticketTotal;
    private Integer ticketLimit;
    private String eventId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(ticketName, ticket.ticketName) &&
                Objects.equals(ticketPrice, ticket.ticketPrice) &&
                Objects.equals(ticketTotal, ticket.ticketTotal) &&
                Objects.equals(ticketLimit, ticket.ticketLimit) &&
                Objects.equals(eventId, ticket.eventId);
    }

    long countUnsoldTicket(List<RegistrationInfo> registrationInfoList) {
        return getTicketTotal() -  registrationInfoList.stream().mapToLong(RegistrationInfo::getTicketCount).sum();
    }
}
