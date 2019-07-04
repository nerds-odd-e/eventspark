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

import javax.servlet.http.HttpSession;

@Controller
public class ImportCsvController {

    @Autowired
    private FileCheckService fileCheckService;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private HttpSession session;

    @GetMapping("/import-csv")
    String getImportCsv(@RequestParam(name = "force", required = false) String force) {

        if (!Boolean.valueOf(force)) {
            System.out.println("Hello WOrld");
        }

        return "import-csv";
    }

    @PostMapping("/import-csv")
    ModelAndView postCsv(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestParam(name = "force", required = false) String force) {
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
            List<String> errors = fileCheckService.checkUploadList(addressItems);
            if (!errors.isEmpty()) {
                model.setViewName("import-csv");
                model.addObject("errors", errors);
//                model.setStatus(HttpStatus.OK);
                return model;
            }

            List<String> duplicates = fileCheckService.checkDuplicateAddress(addressItems);
            if (!duplicates.isEmpty()) {
                model.setViewName("import-csv");
                model.addObject("duplicates", duplicates);
                session.setAttribute("addressItems", addressItems);
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
        model.addObject("contactList", addressBookService.get());
        return model;
    }
}
