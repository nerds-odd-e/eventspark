package com.odde.mailsender.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterForm {

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
