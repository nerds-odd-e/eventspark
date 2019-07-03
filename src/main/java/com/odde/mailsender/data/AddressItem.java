package com.odde.mailsender.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

public class AddressItem implements Serializable {

    private String mailAddress;

    private String name;

    public AddressItem(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    @JsonCreator
    public AddressItem(@JsonProperty("mailAddress") String mailAddress, @JsonProperty("name") String name) {
        this.mailAddress = mailAddress;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressItem that = (AddressItem) o;
        return Objects.equals(mailAddress, that.mailAddress) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(mailAddress, name);
    }

    @Override
    public String toString() {
        return "AddressItem{" +
                "mailAddress='" + mailAddress + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String addressItemToString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON convert error.", e);
        }
    }

    public static AddressItem convertJsonToObject(String address) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(address, AddressItem.class);
        } catch (IOException e) {
            throw new RuntimeException("JSON parse error.", e);
        }
    }


}
