package com.odde.mailsender.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInfo {

    private String firstName;

    private String lastName;

    private String company;

    private String address;
}
