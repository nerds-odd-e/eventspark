package com.odde.mailsender.data;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
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

    private String attendeeFirstName;
    private String attendeeLastName;
    private String attendeeCompany;
    private String attendeeAddress;

    private String ticketId;
    private Integer ticketCount;
    private String eventId;
}
