package com.odde.mailsender.form;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class ContactListForm {
    @NotEmpty(message = "{0} may not be empty")
    @Pattern(regexp = "^([_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,}))?$", message = "{error.invalid.email}")
    private String address;
    private String name;


    public String getAddress() {
        return address;
    }
    public String getName() {
        return name;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public void setName(String name) {
        this.name = name;
    }
}
