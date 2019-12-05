package com.odde.mailsender.controller;

import com.odde.mailsender.data.Address;
import com.odde.mailsender.form.ContactListForm;
import com.odde.mailsender.form.MailSendForm;
import com.odde.mailsender.service.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class ContactListController {

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping("/contact-list")
    public String getContactList(@ModelAttribute("form") ContactListForm form, Model model) {
        model.addAttribute("contactList", addressRepository.findAll());
        return "contact-list";
    }

    @PostMapping("/contact-list")
    public String addContactList(@Valid @ModelAttribute("form") ContactListForm form, BindingResult result, Model model) {
        if(result.hasErrors()) {
            addContactListToModel(model);
            return "contact-list";
        }

        try {
            if(addressRepository.findByNameAndMailAddress(form.getName(), form.getAddress()) == null)
                addressRepository.save(new Address(form.getName(), form.getAddress()));
            else
                throw new Exception("Duplicate address");
        } catch (Exception e) {
            result.rejectValue("","", e.getMessage());
            return "contact-list";
        }

        return "redirect:/contact-list";
    }

    private void addContactListToModel(Model model) {
        model.addAttribute("contactList", addressRepository.findAll());
    }

    @PostMapping("/create-mail")
    public String createEmail(@RequestParam(required = false) String[] mailAddress, Model model) {
        model.addAttribute("form", MailSendForm.create(mailAddress));
        return "home";
    }
}
