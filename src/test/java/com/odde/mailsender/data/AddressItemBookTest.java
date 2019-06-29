package com.odde.mailsender.data;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AddressItemBookTest {

    AddressBook addressBook = new AddressBook();
    AddressItem addressItem = new AddressItem("stanly@odd-e.com", "stanly");


    @Test
    public void addAddressItem() throws Exception {
        this.addressBook.add(addressItem);
        assertThat(addressBook.getAddressItems().get(0).getMailAddress(), is(addressItem.getMailAddress()));
    }

    @Test
    public void existFile() throws Exception {
        this.addressBook.add(addressItem);
        this.addressBook.save();
        assertThat(new File(AddressBook.FILE_PATH).exists(), is(true));
    }

    @Test
    public void saveAddressItem() throws Exception {
        this.addressBook.add(addressItem);
        this.addressBook.save();

        List<String> addressList = FileUtils.readLines(new File(AddressBook.FILE_PATH), "utf-8");

        assertThat(addressList.size(), is(1));
        assertThat(addressList.get(0), is(addressItem.addressItemToString()));
    }

    @Test
    public void loadOneItem() throws Exception {
        addressBook.add(addressItem);
        addressBook.save();

        addressBook.load();

        assertThat(addressBook.getAddressItems().size(), is(1));
        assertThat(addressBook.getAddressItems().get(0).getMailAddress(), is(addressItem.getMailAddress()));
        assertThat(addressBook.getAddressItems().get(0).getName(), is(addressItem.getName()));
    }

    @Test(expected = Exception.class)
    public void checkDuplication() throws Exception {

        addressBook.add(addressItem);
        addressBook.add(addressItem);
    }

    @Test
    public void findByAddress() throws Exception {
        AddressItem actual = addressBook.findByAddress("stanly@odd-e.com");
        assertThat(actual.getName(), is(addressItem.getName()));
        assertThat(actual.getMailAddress(), is(addressItem.getMailAddress()));
    }
}
