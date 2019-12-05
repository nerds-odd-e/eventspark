package com.odde.mailsender.service;

import com.odde.mailsender.data.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;

@Service
public class FileCheckServiceImpl implements FileCheckService {
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<String> checkUploadList(List<Address> uploadList) {
        return Stream.concat(
            checkMailAddressPattern(uploadList).stream(),
            checkDuplicateInUploadList(uploadList).stream()
        ).collect(Collectors.toList());
    }

    @Override
    public List<String> checkDuplicateAddress(List<Address> uploadList) {
        return checkDuplicateWithStoredData(uploadList);
    }

    private List<String> checkDuplicateWithStoredData(List<Address> uploadList) {
        List<String> errors = new ArrayList<>();

        for (Address addressItem : uploadList) {
            Address finds = addressRepository.findByMailAddress(addressItem.getMailAddress());

            if (nonNull(finds)) {
                errors.add("already registered " + finds.getMailAddress());
            }
        }

        return errors;
    }

    private List<String> checkDuplicateInUploadList(List<Address> uploadList) {
        Map<String, List<Integer>> duplicatedMap = new HashMap<>();

        for(int i = 0; i < uploadList.size(); i++) {
            Address item = uploadList.get(i);
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

    private List<String> checkMailAddressPattern(List<Address> uploadList) {
        List<String> result = new ArrayList<>();

        for(Address item : uploadList) {
            if (!item.checkValidMailAddress()) {
                result.add(String.format("%s is invalid address.", item.getMailAddress()));
            }
        }
        return result;
    }
}
