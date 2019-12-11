package com.odde.mailsender.data;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

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
}
