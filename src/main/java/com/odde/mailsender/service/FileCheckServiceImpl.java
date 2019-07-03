package com.odde.mailsender.service;

import static java.util.Objects.nonNull;

import com.odde.mailsender.data.AddressBook;
import com.odde.mailsender.data.AddressItem;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FileCheckServiceImpl implements FileCheckService {


    private static final String MAIL_ADDRESS_PATTERN = "^([_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})" + "(?:;" + "[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})" + ")*)?$";

    @Override
    public List<AddressItem> loadContactList(Path csvFile) throws IOException {
        return Files.readAllLines(csvFile).stream().map(line -> {
            String[] s = line.split(",");
            return new AddressItem(s[0], s[1]);
        }).collect(Collectors.toList());
    }

    @Override
    public List<String> checkUploadList(List<AddressItem> uploadList) {
        List<String> errors = new ArrayList<>();

        List<String> checkMailAddressResult = checkMailAddress(uploadList);
        errors.addAll(checkMailAddressResult);

        List<String> checkDuplicateInUploadListResult = checkDuplicateInUploadList(uploadList);
        errors.addAll(checkDuplicateInUploadListResult);

        List<String> checkDupliateWithStoredDataResult = checkDuplicateWithStoredData(uploadList);
        errors.addAll(checkDupliateWithStoredDataResult);

        return errors;
    }

    private List<String> checkDuplicateWithStoredData(List<AddressItem> uploadList) {
        List<String> errors = new ArrayList<>();

        AddressBook addressBook = new AddressBook();
        List<AddressItem> storedAddressItemList = addressBook.getAddressItems();

        for (AddressItem addressItem : uploadList) {
            AddressItem finds = addressBook.findByAddress(addressItem.getMailAddress());

            if (nonNull(finds)) {
                errors.add("already registered " + finds.getMailAddress());
            }
        }

        return errors;
    }

    private List<String> checkDuplicateInUploadList(List<AddressItem> uploadList) {
        List<String> result = new ArrayList<>();

        Map<String, List<Integer>> duplicatedMap = new HashMap<>();
        for(int i = 0; i < uploadList.size(); i++) {
            AddressItem item = uploadList.get(i);
            if (!duplicatedMap.containsKey(item.getMailAddress())) {
                List<Integer> duplicatedRecords = new ArrayList<>();
                duplicatedRecords.add(i + 1);
                duplicatedMap.put(item.getMailAddress(), duplicatedRecords);
                continue;
            }
            duplicatedMap.get(item.getMailAddress()).add(i + 1);
        }
        StringJoiner joiner = new StringJoiner(" and ");
        for (String key :duplicatedMap.keySet()) {
            if (duplicatedMap.get(key).size() > 1) {
                duplicatedMap.get(key).stream().forEach(i -> joiner.add(String.valueOf(i)));
                result.add(String.format("%s rows are duplicated with %s", joiner.toString(), key));
            }
        }
        return result;
    }

    private List<String> checkMailAddress(List<AddressItem> uploadList) {
        List<String> result = new ArrayList<>();

        for(AddressItem item : uploadList) {
            if (!item.getMailAddress().matches(MAIL_ADDRESS_PATTERN)) {
                result.add(String.format("%s is invalid address.", item.getMailAddress()));
            }
        }

        return result;
    }
}
