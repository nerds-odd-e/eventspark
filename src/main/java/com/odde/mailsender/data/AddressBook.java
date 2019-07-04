package com.odde.mailsender.data;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AddressBook {

    private List<AddressItem> addressItems = new ArrayList<>();
    public static final String FILE_PATH = System.getenv("HOME") + "/workspace/eventspark/addressbook.json";

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

    public int update(List<AddressItem> requestAddressItems) throws IOException {
        ArrayList<AddressItem> insertList = generateInsertItems(requestAddressItems);
        ArrayList<AddressItem> updateList = generateUpdateItems(requestAddressItems);
        updateName(updateList);
        this.addressItems = Stream.concat(insertList.stream(), updateList.stream()).collect(Collectors.toList());
        save();
        return requestAddressItems.size();
    }

    private void updateName(ArrayList<AddressItem> updateList) {
        updateList.forEach(item->{
            Optional<AddressItem> target = this.addressItems.stream().filter(i -> i.getMailAddress().equals(item.getMailAddress())).findFirst();
            target.get().setName(item.getName());
        });
    }

    private ArrayList<AddressItem> generateUpdateItems(List<AddressItem> requestAddressItems) {
        ArrayList<AddressItem> updateList = new ArrayList<>();
        requestAddressItems.forEach(requestAddressItem->{
            if(this.addressItems.stream().anyMatch(o->o.getMailAddress().equals(requestAddressItem.getMailAddress()))){
                updateList.add((requestAddressItem));
            }
        });
        return updateList;
    }

    private ArrayList<AddressItem> generateInsertItems(List<AddressItem> requestAddressItems) {
        ArrayList<AddressItem> insertList = new ArrayList<>();
        requestAddressItems.forEach(requestAddressItem->{
            if(this.addressItems.stream().noneMatch(o->o.getMailAddress().equals(requestAddressItem.getMailAddress()))){
                insertList.add((requestAddressItem));
            }
        });
        return insertList;
    }
}
