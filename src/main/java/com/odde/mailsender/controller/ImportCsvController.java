package com.odde.mailsender.controller;

import com.fasterxml.jackson.dataformat.csv.CsvMappingException;
import com.odde.mailsender.InvalidContactCsvHeaderException;
import com.odde.mailsender.data.AddressItem;
import com.odde.mailsender.form.ContactCsvFile;
import com.odde.mailsender.form.ContactListForm;
import com.odde.mailsender.service.AddressBookService;
import com.odde.mailsender.service.FileCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.CharConversionException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Controller
public class ImportCsvController {

    @Autowired
    private FileCheckService fileCheckService;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private HttpSession session;

    @GetMapping("/import-csv")
    String getImportCsv() {
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
                return errorModel("system error is occurred. Please upload again.", "import-csv");
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
            @RequestParam(name = "file", required = false) MultipartFile multipartFile) throws IOException {

        ContactCsvFile contactCsvFile = new ContactCsvFile(multipartFile);

        if (!contactCsvFile.nameIsCsv()) {
            return errorModel("Please specify csv file.", "import-csv");
        }
        List<AddressItem> addressItems = contactCsvFile.parseCsv();

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
        model.addObject("errors", Collections.singletonList(errorMessage));
        model.setStatus(HttpStatus.BAD_REQUEST);
        return model;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView headerInvalid(InvalidContactCsvHeaderException e) {
        return errorModel("CSV file header requires mail,name.", "import-csv");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView binaryError(CharConversionException e) {
        return errorModel("Uploaded file is binary data.", "import-csv");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView unexpectedError(IOException e) {
        return errorModel("Unexpected error, please retry.", "import-csv");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView unmatchedField(CsvMappingException e) {
        return errorModel("CSV must have 2 fields(mail,name).", "import-csv");
    }
}
