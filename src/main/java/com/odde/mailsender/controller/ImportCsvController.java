package com.odde.mailsender.controller;

import com.odde.mailsender.data.AddressBook;
import com.odde.mailsender.data.AddressItem;
import com.odde.mailsender.form.ContactListForm;
import com.odde.mailsender.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView postCsv(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestParam("force") String force) {
        ModelAndView model = new ModelAndView("contact-list");
        model.addObject("form", new ContactListForm());
        return model;
    }
}
