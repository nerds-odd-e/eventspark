package com.odde.mailsender.service;

import com.odde.mailsender.data.AddressItem;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface FileCheckService {

    List<AddressItem> loadContactList(Path csvFile) throws IOException;

    List<String> checkUploadList(List<AddressItem> uploadList);
}
