package com.odde.mailsender.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@Getter
public class Address {
  public static final String MAIL_ADDRESS_PATTERN = "^([_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
          + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})" + "(?:;" + "[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
          + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})" + ")*)?$";

  @Id
  private String id;

  private String name;
  private String mailAddress;

  @JsonCreator
  public Address(@JsonProperty("name") String name, @JsonProperty("mailAddress") String mailAddress) {
    this.name = name;
    this.mailAddress = mailAddress;
  }

  public boolean checkValidMailAddress() {
    return this.mailAddress.matches(MAIL_ADDRESS_PATTERN);
  }
}
