package com.odde.mailsender.service;

import com.odde.mailsender.data.AddressBook;
import com.odde.mailsender.data.AddressItem;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileCheckServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private FileCheckService target;

    @Test
    public void checkSuccessful() throws Exception {
        List<AddressItem> uploadList = new ArrayList<>();
        uploadList.add(new AddressItem("jun.murakami@g.softbank.co.jp", "Jun Murakami"));
        uploadList.add(new AddressItem("shigeru.tatsuta@g.softbank.co.jp", "Shigeru Tatsuta"));

        List<String> actual = target.checkUploadList(uploadList);

        List<String> expected = new ArrayList<>();
        assertThat(expected, is(actual));

    }

    @Test
    public void checkFailedWithInvalidMailAddress() throws Exception {
        List<AddressItem> uploadList = new ArrayList<>();
        uploadList.add(new AddressItem("jun.murakami@g.softbank.co.jp", "Jun Murakami"));
        uploadList.add(new AddressItem("shigeru.tatsuta#g.softbank.co.jp", "Shigeru Tatsuta"));

        List<String> actual = target.checkUploadList(uploadList);

        List<String> expected = Arrays.asList("shigeru.tatsuta#g.softbank.co.jp is invalid address.");
        assertThat(expected, is(actual));
    }

    @Test
    public void checkDuplicateMailAddressInUploadList() throws Exception {
        List<AddressItem> uploadList = new ArrayList<>();
        uploadList.add(new AddressItem("jun.murakami@g.softbank.co.jp", "Jun Murakami"));
        uploadList.add(new AddressItem("shigeru.tatsuta@g.softbank.co.jp", "Shigeru Tatsuta"));
        uploadList.add(new AddressItem("jun.murakami@g.softbank.co.jp", "Jun Murakami2"));

        List<String> actual = target.checkUploadList(uploadList);

        List<String> expected = Arrays.asList("1 and 3 rows are duplicated with jun.murakami@g.softbank.co.jp");
        assertThat(expected, is(actual));
    }

    @Test
    public void checkDuplicatedOneMailAddressStoredData() throws Exception {
        AddressBook addressBook = new AddressBook();
        addressBook.add(new AddressItem("jun.murakami@g.softbank.co.jp", "Jun Murakami"));
        addressBook.add(new AddressItem("shigeru.tatsuta@g.softbank.co.jp", "Shigeru Tatsuta"));
        addressBook.save();

        List<AddressItem> uploadList = new ArrayList<>();
        uploadList.add(new AddressItem("jun.murakami@g.softbank.co.jp", "Jun Murakami"));

        List<String> actual = target.checkUploadList(uploadList);

        List<String> expected = Arrays.asList("already registered jun.murakami@g.softbank.co.jp");
        assertThat(expected, is(actual));

        addressBook = new AddressBook();
        addressBook.save();
    }

    @Test
    public void checkDuplicatedMultiMailAddressStoredData() throws Exception {
        AddressBook addressBook = new AddressBook();
        addressBook.add(new AddressItem("jun.murakami@g.softbank.co.jp", "Jun Murakami"));
        addressBook.add(new AddressItem("ryota.saiga@g.softbank.co.jp", "Ryota Saiga"));
        addressBook.add(new AddressItem("shigeru.tatsuta@g.softbank.co.jp", "Shigeru Tatsuta"));
        addressBook.save();

        List<AddressItem> uploadList = new ArrayList<>();
        uploadList.add(new AddressItem("jun.murakami@g.softbank.co.jp", "Jun Murakami"));
        uploadList.add(new AddressItem("shigeru.tatsuta@g.softbank.co.jp", "Shigeru Tatsuta"));

        List<String> actual = target.checkUploadList(uploadList);

        List<String> expected = Arrays.asList("already registered jun.murakami@g.softbank.co.jp", "already registered shigeru.tatsuta@g.softbank.co.jp");
        assertThat(expected, is(actual));

        addressBook = new AddressBook();
        addressBook.save();
    }

}