package com.odde.mailsender.controller;

import com.odde.mailsender.data.AddressItem;
import com.odde.mailsender.form.ContactListForm;
import com.odde.mailsender.form.MailSendForm;
import com.odde.mailsender.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ImportCsvController {

    //@Autowired
    //private AddressBookService addressBookService;

    @GetMapping("/import-csv")
    public String getImportCsv() {
        //model.addAttribute("contactList", addressBookService.get());
        return "import-csv";
    }

    @PostMapping("/import-csv")
    public ModelAndView postCsv(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestParam("force") String force) {
        ModelAndView model = new ModelAndView("contact-list");
        model.addObject("form", new ContactListForm());
        return model;
    }
}
