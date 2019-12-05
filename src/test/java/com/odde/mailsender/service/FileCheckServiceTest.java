package com.odde.mailsender.service;

import com.odde.mailsender.data.Address;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class FileCheckServiceTest {
    @Autowired
    private AddressRepository addressRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private FileCheckService target;

    @Before
    public void setUp() {
        addressRepository.deleteAll();
    }

    @Test
    public void checkSuccessful() {
        List<DataPattern> dataPatternList = Arrays.asList(
            new DataPattern("ty@example.com", new ArrayList<>()),
            new DataPattern("ty#example.com", Collections.singletonList("ty#example.com is invalid address."))
        );

        dataPatternList.forEach(dataPattern -> {
            List<Address> uploadList = new ArrayList<>();
            uploadList.add(new Address("Taro Yamada", dataPattern.mailAddress));
            assertThat(target.checkUploadList(uploadList), is(dataPattern.expected));
        });
    }

    private class DataPattern {
        private final String mailAddress;
        private final List<String> expected;

        public DataPattern(String mailAddress, List<String> expected) {
            this.mailAddress = mailAddress;
            this.expected = expected;
        }
    }

    @Test
    public void checkDuplicateMailAddressInUploadList() {
        List<Address> uploadList = new ArrayList<>();
        uploadList.add(new Address("Hanako Suzuki", "ty@example.com"));
        uploadList.add(new Address("Taro Yamada", "hnk@example.com"));
        uploadList.add(new Address("Hanako Suzuki2", "ty@example.com"));

        List<String> actual = target.checkUploadList(uploadList);
        List<String> expected = Collections.singletonList("1 and 3 rows are duplicated with ty@example.com");

        assertThat(actual, is(expected));
    }

    @Test
    public void checkDuplicatedOneMailAddressStoredData() throws Exception {
        addressRepository.save(new Address("Hanako Suzuki", "ty@example.com"));
        addressRepository.save(new Address("Taro Yamada", "hnk@example.com"));

        List<Address> uploadList = new ArrayList<>();
        uploadList.add(new Address("Hanako Suzuki", "ty@example.com"));

        List<String> actual = target.checkDuplicateAddress(uploadList);

        List<String> expected = Collections.singletonList("already registered ty@example.com");
        assertThat(actual, is(expected));
    }

    @Test
    public void checkDuplicatedMultiMailAddressStoredData() throws Exception {
        addressRepository.save(new Address("Hanako Suzuki", "ty@example.com"));
        addressRepository.save(new Address("Sato", "st@example.com"));
        addressRepository.save(new Address("Taro Yamada", "hnk@example.com,Hanako Suzuki"));

        List<Address> uploadList = new ArrayList<>();
        uploadList.add(new Address( "Hanako Suzuki", "ty@example.com"));
        uploadList.add(new Address("Taro Yamada", "hnk@example.com,Hanako Suzuki" ));

        List<String> actual = target.checkDuplicateAddress(uploadList);

        List<String> expected = Arrays.asList("already registered ty@example.com", "already registered hnk@example.com,Hanako Suzuki");
        assertThat(actual, is(expected));
    }
}