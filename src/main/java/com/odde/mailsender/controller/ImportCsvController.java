package com.odde.mailsender.controller;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMappingException;
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

import java.io.CharConversionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

        return "import-csv";
    }

    @PostMapping("/import-from-session")
    ModelAndView postSession() {
        List <AddressItem> sessionAddressItems = (List<AddressItem>) session.getAttribute("addressItems");
        ModelAndView model = new ModelAndView();
        if (!sessionAddressItems.isEmpty()) {
            sessionAddressItems.stream().forEach(addressItem -> {
                try {
                    addressBookService.add(addressItem);
                } catch (Exception e) {
                    // TODO:atode
                    e.printStackTrace();
                }
            });
        }
        model.setViewName("contact-list");
        model.addObject("form", new ContactListForm());
        model.addObject("contactList", addressBookService.get());
        model.addObject("successCount", sessionAddressItems.size());
        return model;

    }

    @PostMapping("/import-csv")
    ModelAndView postCsv(
            @RequestParam(name = "file", required = false) MultipartFile multipartFile,
            @RequestParam(name = "force", required = false) String force) {
        ModelAndView model = new ModelAndView();
        String filename = multipartFile.getOriginalFilename();
        if (!filename.endsWith(".csv")) {
            List<String> errors = Arrays.asList("Please specify csv file.");
            model.setViewName("import-csv");
            model.addObject("errors", errors);
            model.setStatus(HttpStatus.BAD_REQUEST);
            return model;
        }
        List<AddressItem> addressItems = null;
        try {
            MappingIterator<AddressItem> personIter = new CsvMapper().readerWithTypedSchemaFor(AddressItem.class).readValues(multipartFile.getInputStream());
            addressItems = personIter.readAll();

            if (!addressItems.get(0).getMailAddress().equals("mail") ||
                    !addressItems.get(0).getName().equals("name")) {
                List<String> errors = Arrays.asList("CSV file header requires mail,name.");
                model.setViewName("import-csv");
                model.addObject("errors", errors);
                model.setStatus(HttpStatus.BAD_REQUEST);
                return model;
            } else {
                addressItems.remove(0);
            }
        } catch (CsvMappingException e) {
            List<String> errors = Arrays.asList("CSV must have 2 fields(mail,name).");
            model.setViewName("import-csv");
            model.addObject("errors", errors);
            model.setStatus(HttpStatus.BAD_REQUEST);
            return model;
        } catch (CharConversionException e) {
            List<String> errors = Arrays.asList("Uploaded file is binary data.");
            model.setViewName("import-csv");
            model.addObject("errors", errors);
            model.setStatus(HttpStatus.BAD_REQUEST);
            return model;
        } catch (IOException e) {
            List<String> errors = Arrays.asList("Unexpected error, please retry.");
            model.setViewName("import-csv");
            model.addObject("errors", errors);
            model.setStatus(HttpStatus.BAD_REQUEST);
            return model;
        }
        List<String> errors = fileCheckService.checkUploadList(addressItems);
        if (!errors.isEmpty()) {
            model.setViewName("import-csv");
            model.addObject("errors", errors);
            return model;
        }

        List<String> duplicates = fileCheckService.checkDuplicateAddress(addressItems);
        if (!duplicates.isEmpty()) {
            model.setViewName("import-csv");
            model.addObject("duplicates", duplicates);
            session.setAttribute("addressItems", addressItems);
            return model;
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
        model.addObject("successCount", addressItems.size());
        return model;
    }
}
