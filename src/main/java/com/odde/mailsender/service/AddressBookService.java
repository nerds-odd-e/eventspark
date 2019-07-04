package com.odde.mailsender.service;

import com.odde.mailsender.data.AddressItem;

import java.io.IOException;
import java.util.List;

public interface AddressBookService {


    void add(AddressItem addressItem) throws Exception;

    List<AddressItem> get();

    AddressItem findByAddress(String address);

    int update(List<AddressItem> addressItems)throws IOException;
}
