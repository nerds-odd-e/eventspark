package com.odde.mailsender.form;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.odde.mailsender.InvalidContactCsvHeaderException;
import com.odde.mailsender.data.Address;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public class ContactCsvFile {
    private MultipartFile multipartFile;
    private CsvSchema schema = CsvSchema.builder().addColumn("mailAddress").addColumn("name").build();

    public ContactCsvFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    private boolean isValidHeader(List<Address> addressItems) {
        return addressItems.get(0).getMailAddress().equals("mail") && addressItems.get(0).getName().equals("name");
    }

    public List<Address> parseCsv() throws IOException, InvalidContactCsvHeaderException {
        List<Address> addressItems = new CsvMapper().readerFor(Address.class).with(schema).<Address>readValues(multipartFile.getInputStream()).readAll();
        if(isValidHeader(addressItems))
            addressItems.remove(0);
        else
            throw new InvalidContactCsvHeaderException();

        return addressItems;
    }

    public boolean nameIsCsv() {
        return multipartFile.getOriginalFilename().endsWith(".csv");
    }

}
