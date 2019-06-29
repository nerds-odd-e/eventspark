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

import javax.validation.Valid;
import java.util.List;

@Controller
public class ContactListController {

    @Autowired
    private AddressBookService addressBookService;

    @GetMapping("/contact-list")
    public String getContactList(@ModelAttribute("form") ContactListForm form, Model model) {
        model.addAttribute("contactList", addressBookService.get());
        return "contact-list";
    }

    @PostMapping("/contact-list")
    public String addContactList(@Valid @ModelAttribute("form") ContactListForm form, BindingResult result, Model model) {
        if(result.hasErrors()) {
            addContactListToModel(model);
            return "contact-list";
        }

        try {
            addressBookService.add(new AddressItem(form.getAddress(), form.getName()));
        } catch (Exception e) {
            result.rejectValue("","", e.getMessage());
            return "contact-list";
        }

        return "redirect:/contact-list";
    }

    private void addContactListToModel(Model model) {
        model.addAttribute("contactList", addressBookService.get());
    }

    @PostMapping("/create-mail")
    public String createEmail(@RequestParam(required = false) String[] mailAddress, Model model) {
        model.addAttribute("form", MailSendForm.create(mailAddress));
        return "send";
    }
}
