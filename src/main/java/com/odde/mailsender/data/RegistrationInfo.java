package com.odde.mailsender.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@Getter
public class RegistrationInfo {
    public static final String MAIL_ADDRESS_PATTERN = "^([_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})" + "(?:;" + "[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})" + ")*)?$";

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String company;
    private String address;
    private String ticketType;
    private Integer ticketCount;
    private String eventId;

    @JsonCreator
    public RegistrationInfo(@JsonProperty("firstName") String firstName,
                            @JsonProperty("lastName") String lastName,
                            @JsonProperty("company") String company,
                            @JsonProperty("address") String address,
                            @JsonProperty("ticketType") String ticketType,
                            @JsonProperty("ticketCount") Integer ticketCount,
                            @JsonProperty("eventId") String eventId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.address = address;
        this.ticketType = ticketType;
        this.ticketCount = ticketCount;
        this.eventId = eventId;
    }

    public boolean checkValidMailAddress(){
        return this.address.matches(MAIL_ADDRESS_PATTERN);
    }
}
