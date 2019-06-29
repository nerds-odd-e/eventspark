package com.odde.mailsender.service;

import com.odde.mailsender.data.AddressBook;
import com.odde.mailsender.data.AddressItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {


    private AddressBook addressBook  = new AddressBook();

    @Override
    public void add(AddressItem addressItem) throws Exception {
        addressBook.load();
        addressBook.add(addressItem);
        addressBook.save();
    }

    @Override
    public List<AddressItem> get() {
        return addressBook.getAddressItems();
    }

    public AddressItem findByAddress(String address) {
        return addressBook.findByAddress(address);
    }
}
