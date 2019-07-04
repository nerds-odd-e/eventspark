package com.odde.mailsender.service;

import com.odde.mailsender.data.AddressBook;
import com.odde.mailsender.data.AddressItem;
import org.junit.Before;
import org.junit.Ignore;
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
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileCheckServiceTest {

    private AddressBook addressBook;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private FileCheckService target;

    @Before
    public void setUp() {
        addressBook = new AddressBook();
    }

    @Test
    public void checkSuccessful() {
        List<AddressItem> uploadList = new ArrayList<>();
        uploadList.add(new AddressItem("ty@example.com", "Taro Yamada"));
        uploadList.add(new AddressItem("hnk@example.com", "Hanako Suzuki"));

        List<String> actual = target.checkUploadList(uploadList);

        List<String> expected = new ArrayList<>();
        assertThat(actual, is(expected));

    }

    @Test
    public void checkFailedWithInvalidMailAddress() {
        List<AddressItem> uploadList = new ArrayList<>();
        uploadList.add(new AddressItem("ty@example.com", "Hanako Suzuki"));
        uploadList.add(new AddressItem("ty#example.com", "Taro Yamada"));

        List<String> actual = target.checkUploadList(uploadList);

        List<String> expected = Collections.singletonList("ty#example.com is invalid address.");
        assertThat(actual, is(expected));
    }

    @Test
    public void checkDuplicateMailAddressInUploadList() {
        List<AddressItem> uploadList = new ArrayList<>();
        uploadList.add(new AddressItem("ty@example.com", "Hanako Suzuki"));
        uploadList.add(new AddressItem("hnk@example.com", "Taro Yamada"));
        uploadList.add(new AddressItem("ty@example.com", "Hanako Suzuki2"));

        List<String> actual = target.checkUploadList(uploadList);

        List<String> expected = Collections.singletonList("1 and 3 rows are duplicated with ty@example.com");
        assertThat(actual, is(expected));
    }

    @Test
    public void checkDuplicatedOneMailAddressStoredData() throws Exception {
        addressBook.add(new AddressItem("ty@example.com", "Hanako Suzuki"));
        addressBook.add(new AddressItem("hnk@example.com,Hanako Suzuki", "Taro Yamada"));
        addressBook.save();

        List<AddressItem> uploadList = new ArrayList<>();
        uploadList.add(new AddressItem("ty@example.com", "Hanako Suzuki"));

        List<String> actual = target.checkDuplicateAddress(uploadList);

        List<String> expected = Collections.singletonList("already registered ty@example.com");
        assertThat(actual, is(expected));

        addressBook = new AddressBook();
        addressBook.save();
    }

    @Test
    public void checkDuplicatedMultiMailAddressStoredData() throws Exception {
        addressBook.add(new AddressItem("ty@example.com", "Hanako Suzuki"));
        addressBook.add(new AddressItem("st@example.com", "Sato"));
        addressBook.add(new AddressItem("hnk@example.com,Hanako Suzuki", "Taro Yamada"));
        addressBook.save();

        List<AddressItem> uploadList = new ArrayList<>();
        uploadList.add(new AddressItem("ty@example.com", "Hanako Suzuki"));
        uploadList.add(new AddressItem("hnk@example.com,Hanako Suzuki", "Taro Yamada"));

        List<String> actual = target.checkDuplicateAddress(uploadList);

        List<String> expected = Arrays.asList("already registered ty@example.com", "already registered hnk@example.com,Hanako Suzuki");
        assertThat(actual, is(expected));

        addressBook = new AddressBook();
        addressBook.save();
    }

}