package com.odde.mailsender.data;

import com.odde.mailsender.service.MailInfo;
import lombok.Builder;
import lombok.Data;

import org.springframework.data.annotation.Id;

import java.util.Objects;

@Data
@Builder
public class Ticket {

    @Id
    private String id;

    private String ticketName;
    private long ticketPrice;
    private long ticketTotal;
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

}
