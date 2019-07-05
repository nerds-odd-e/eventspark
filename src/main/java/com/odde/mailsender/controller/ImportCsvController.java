package com.odde.mailsender.controller;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMappingException;
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
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

import static java.util.Arrays.*;

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
            try {
                addressBookService.update(sessionAddressItems);
            } catch (IOException e) {
                // TODO:atode
                e.printStackTrace();
            }
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

        String filename = multipartFile.getOriginalFilename();
        if (!filename.endsWith(".csv")) {
            return errorModel("Please specify csv file.", "import-csv");
        }
        List<AddressItem> addressItems = null;
        try {
            MappingIterator<AddressItem> personIter = new CsvMapper().readerWithTypedSchemaFor(AddressItem.class).readValues(multipartFile.getInputStream());
            addressItems = personIter.readAll();

            if (!addressItems.get(0).getMailAddress().equals("mail") || !addressItems.get(0).getName().equals("name")) {
                ModelAndView model = errorModel("CSV file header requires mail,name.", "import-csv");
                return model;
            } else {
                addressItems.remove(0);
            }
        } catch (CsvMappingException e) {
            return errorModel("CSV must have 2 fields(mail,name).", "import-csv");
        } catch (CharConversionException e) {
            return errorModel("Uploaded file is binary data.", "import-csv");
        } catch (IOException e) {
            return errorModel("Unexpected error, please retry.", "import-csv");
        }
        List<String> errors = fileCheckService.checkUploadList(addressItems);
        if (!errors.isEmpty()) {
            ModelAndView model = new ModelAndView();
            model.setViewName("import-csv");
            model.addObject("errors", errors);
            return model;
        }

        List<String> duplicates = fileCheckService.checkDuplicateAddress(addressItems);
        if (!duplicates.isEmpty()) {
            ModelAndView model = new ModelAndView();
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

        ModelAndView model = new ModelAndView();
        model.setViewName("contact-list");
        model.addObject("form", new ContactListForm());
        model.addObject("contactList", addressBookService.get());
        model.addObject("successCount", addressItems.size());
        return model;
    }

    private ModelAndView errorModel(String errorMessage, String viewName) {
        ModelAndView model = new ModelAndView();
        model.setViewName(viewName);
        model.addObject("errors", asList(errorMessage));
        model.setStatus(HttpStatus.BAD_REQUEST);
        return model;
    }

}
