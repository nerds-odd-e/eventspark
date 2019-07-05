package com.odde.mailsender.service;

import static java.util.Objects.nonNull;

import com.odde.mailsender.data.AddressBook;
import com.odde.mailsender.data.AddressItem;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FileCheckServiceImpl implements FileCheckService {


    @Override
    public List<String> checkUploadList(List<AddressItem> uploadList) {
        List<String> errors = new ArrayList<>();

        List<String> checkMailAddressPatternResult = checkMailAddressPattern(uploadList);
        errors.addAll(checkMailAddressPatternResult);

        List<String> checkDuplicateInUploadListResult = checkDuplicateInUploadList(uploadList);
        errors.addAll(checkDuplicateInUploadListResult);

        return errors;
    }

    @Override
    public List<String> checkDuplicateAddress(List<AddressItem> uploadList) {
        List<String> errors = new ArrayList<>();

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
        return createDuplicateErrorMessage(duplicatedMap);
    }

    private List<String> createDuplicateErrorMessage(Map<String, List<Integer>> duplicatedMap) {
        List<String> result = new ArrayList<>();
        StringJoiner joiner = new StringJoiner(" and ");
        for (String key :duplicatedMap.keySet()) {
            if (duplicatedMap.get(key).size() > 1) {
                duplicatedMap.get(key).forEach(i -> joiner.add(String.valueOf(i)));
                result.add(String.format("%s rows are duplicated with %s", joiner.toString(), key));
            }
        }
        return result;
    }

    private List<String> checkMailAddressPattern(List<AddressItem> uploadList) {
        List<String> result = new ArrayList<>();

        for(AddressItem item : uploadList) {
            if (!item.checkValidMailAddress()) {
                result.add(String.format("%s is invalid address.", item.getMailAddress()));
            }
        }
        return result;
    }
}
