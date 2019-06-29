package com.odde.mailsender.service;

import com.odde.mailsender.data.AddressItem;

import java.util.List;

public interface AddressBookService {


    void add(AddressItem addressItem) throws Exception;

    List<AddressItem> get();

    AddressItem findByAddress(String address);
}
