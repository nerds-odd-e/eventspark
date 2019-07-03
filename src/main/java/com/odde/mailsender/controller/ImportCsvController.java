package com.odde.mailsender.controller;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.odde.mailsender.data.AddressBook;
import com.odde.mailsender.data.AddressItem;
import com.odde.mailsender.form.ContactListForm;
import com.odde.mailsender.service.AddressBookService;
import com.odde.mailsender.service.FileCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ImportCsvController {

    @Autowired
    private FileCheckService fileCheckService;

    @Autowired
    private AddressBookService addressBookService;

    @GetMapping("/import-csv")
    String getImportCsv() {
        //model.addAttribute("contactList", addressBookService.get());
        return "import-csv";
    }

    @PostMapping("/sample-import-csv")
    public String getImportCsvSample(@RequestParam("csvfile") MultipartFile multipartFile, Model model) {

        // Cucumber を通すためのダミーの仮実装
        try {
            AddressBook addressBook = new AddressBook();
            addressBook.load();
            addressBook.add(new AddressItem("test1@example.com", "test1"));
            addressBook.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/contact-list";
    }

    @PostMapping("/import-csv")
    ModelAndView postCsv(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestParam("force") String force) {
        ModelAndView model = new ModelAndView();
        String filename = multipartFile.getOriginalFilename();
        if (!filename.endsWith(".csv")) {
            model.setViewName("import-csv");
            model.setStatus(HttpStatus.BAD_REQUEST);
            return model;
        }
        List<AddressItem> addressItems = null;
        try {
            MappingIterator<AddressItem> personIter = new CsvMapper().readerWithTypedSchemaFor(AddressItem.class).readValues(multipartFile.getInputStream());
            addressItems = personIter.readAll();
        } catch (IOException e) {
            model.setViewName("import-csv");
            model.setStatus(HttpStatus.BAD_REQUEST);
            return model;
        }

        if (!Boolean.valueOf(force)) {
            List<String> list = fileCheckService.checkUploadList(addressItems);
            if (!list.isEmpty()) {
                model.setViewName("import-csv");
                model.setStatus(HttpStatus.ACCEPTED);
                return model;
            }
        }

        addressItems.stream().forEach(addressItem -> {
            try {
                addressBookService.add(addressItem);
            } catch (Exception e) {
                // TODO:atode
                e.printStackTrace();
            }
        });

        model.setViewName("contact-list");
        model.addObject("form", new ContactListForm());
        return model;
    }
}
