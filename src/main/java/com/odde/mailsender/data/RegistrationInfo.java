package com.odde.mailsender.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@Getter
public class RegistrationInfo {
    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String company;
    private String address;
    private String ticketId;
    /**
     * @deprecated only ticketId should be used.
     */
    private String ticketType;
    private Integer ticketCount;
    private String eventId;

    @JsonCreator
    public RegistrationInfo(@JsonProperty("firstName") String firstName,
                            @JsonProperty("lastName") String lastName,
                            @JsonProperty("company") String company,
                            @JsonProperty("address") String address,
                            @JsonProperty("ticketId") String ticketId,
                            @JsonProperty("ticketType") String ticketType,
                            @JsonProperty("ticketCount") Integer ticketCount,
                            @JsonProperty("eventId") String eventId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.address = address;
        this.ticketId = ticketId;
        this.ticketType = ticketType;
        this.ticketCount = ticketCount;
        this.eventId = eventId;
    }
}
