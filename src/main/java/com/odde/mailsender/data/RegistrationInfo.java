package com.odde.mailsender.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
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
}
