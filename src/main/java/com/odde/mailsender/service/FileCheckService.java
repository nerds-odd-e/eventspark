package com.odde.mailsender.service;

import com.odde.mailsender.data.Address;

import java.util.List;

public interface FileCheckService {
    List<String> checkUploadList(List<Address> uploadList);

    List<String> checkDuplicateAddress(List<Address> uploadList);

}
