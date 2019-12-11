package com.odde.mailsender.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@Getter
public class Ticket {

    @Id
    private String id;

    private String ticketName;
    private long ticketPrice;
    private long ticketTotal;
    private Integer ticketLimit;
    private String eventId;

    @JsonCreator
    public Ticket(@JsonProperty("ticketName") String ticketName,
                  @JsonProperty("ticketPrice") long ticketPrice,
                  @JsonProperty("ticketTotal") long ticketTotal,
                  @JsonProperty("ticketLimit") Integer ticketLimit,
                  @JsonProperty("eventId") String eventId) {
        this.ticketName = ticketName;
        this.ticketPrice = ticketPrice;
        this.ticketTotal = ticketTotal;
        this.ticketLimit = ticketLimit;
        this.eventId = eventId;
    }

}
