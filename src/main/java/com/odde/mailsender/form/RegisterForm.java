package com.odde.mailsender.form;

import com.odde.mailsender.data.RegistrationInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterForm {

    private UserInfo buyer;

    private UserInfo attendee;

    private String ticketId;

    private String eventId;

    public int getTicketCount(){
        return 1;
    }

    public RegistrationInfo toEntity() {
        return RegistrationInfo.builder()
                               .firstName(this.buyer.getFirstName())
                               .lastName(this.buyer.getLastName())
                               .company(this.buyer.getCompany())
                               .address(this.buyer.getAddress())
                               .attendeeFirstName(this.attendee.getFirstName())
                               .attendeeLastName(this.attendee.getLastName())
                               .attendeeCompany(this.attendee.getCompany())
                               .attendeeAddress(this.attendee.getAddress())
                               .ticketId(this.ticketId)
                               .ticketCount(this.getTicketCount())
                               .eventId(this.eventId)
                               .build();
    }
}
