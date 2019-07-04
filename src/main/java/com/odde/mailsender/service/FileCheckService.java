package com.odde.mailsender.service;

import com.odde.mailsender.data.AddressItem;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface FileCheckService {
    List<String> checkUploadList(List<AddressItem> uploadList);

    List<String> checkDuplicateAddress(List<AddressItem> uploadList);

}
