package com.odde.mailsender.controller;

import com.odde.mailsender.data.AddressItem;
import com.odde.mailsender.form.ContactListForm;
import com.odde.mailsender.form.MailSendForm;
import com.odde.mailsender.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Controller
public class ImportCsvController {

    @Autowired
    private AddressBookService addressBookService;

    @GetMapping("/import-csv")
    public String getImportCsv() {
        //model.addAttribute("contactList", addressBookService.get());
        return "import-csv";
    }

    @PostMapping("/sample-import-csv")
    public String getImportCsvSample(@RequestParam("csvfile") MultipartFile multipartFile, Model model) {
        //model.addAttribute("contactList", addressBookService.get());
        return "redirect:/contact-list";
    }
}
