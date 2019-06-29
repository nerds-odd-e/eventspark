package com.odde.mailsender.data;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AddressBook {

    private List<AddressItem> addressItems = new ArrayList<>();
    public static final String FILE_PATH = System.getenv("HOME") + "/course-mailer/addressbook.json";

    public void load() {
        clearAddressBookItems();

        readAddressBookFile().forEach(address -> {
            try {
                add(AddressItem.convertJsonToObject(address));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void add(AddressItem addressItem) throws Exception {
        for (AddressItem item : this.addressItems) {
            if (item.getMailAddress().equals(addressItem.getMailAddress())) {
                throw new Exception("Duplicate address");
            }
        }
        this.addressItems.add(addressItem);
    }

    public boolean save() throws IOException {
        File file = new File(FILE_PATH);

        File directory = new File(file.getParent());
        directory.mkdirs();

        if (!file.exists() && !file.createNewFile()) {
            return false;
        }

        try (BufferedWriter writer = getWriter(file)) {
            for (AddressItem addressItem : addressItems) {
                writer.write(addressItem.addressItemToString());
                writer.newLine();
            }

            addressItems.clear();
        }

        return true;
    }

    public AddressItem findByAddress(String address) {
        load();
        for (AddressItem addressItem : addressItems) {
            if (addressItem.getMailAddress().equals(address)) {
                return addressItem;
            }
        }
        return null;
    }

    public List<AddressItem> getAddressItems() {
        load();
        return addressItems;
    }

    private void clearAddressBookItems() {
        if (!addressItems.isEmpty()) {
            addressItems.clear();
        }
    }

    private List<String> readAddressBookFile() {
        List<String> addressList = new ArrayList<>();
        try {
            addressList = FileUtils.readLines(new File(FILE_PATH), "utf-8");
        } catch (FileNotFoundException e) {
            System.err.println("WARN: File not found. " + FILE_PATH);
        } catch (IOException e) {
            throw new RuntimeException("WARN: File read error. " + FILE_PATH, e);
        }
        return addressList;
    }

    private BufferedWriter getWriter(File file) throws UnsupportedEncodingException, FileNotFoundException {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
    }

}
