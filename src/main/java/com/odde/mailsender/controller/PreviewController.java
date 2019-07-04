package com.odde.mailsender.controller;

import com.odde.mailsender.form.MailSendForm;
import com.odde.mailsender.service.AddressBookService;
import com.odde.mailsender.service.MailInfo;
import com.odde.mailsender.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class PreviewController {
    @Autowired
    private AddressBookService addressBookService;

    @PostMapping("/preview/{index}")
    public String preview(@Valid @ModelAttribute("form") MailSendForm form, BindingResult result, Model model, @PathVariable int index) {

        if (result.hasErrors()) {
            return "home";
        }

        String[] addressArr = form.getAddresses();
        String address = addressArr[index];

        if(form.isTemplate()) {
            MailInfo info = form.createRenderedMail(addressBookService.findByAddress(address));
            setModelAttributes(model, info.getTo(), info.getSubject(), info.getBody(), index, addressArr.length);
        } else {
            setModelAttributes(model, address, form.getSubject(), form.getBody(), index, addressArr.length);
        }

        return "preview";
    }

    private void setModelAttributes(Model model, String address, String subject, String body, int index, int arrLength) {
        model.addAttribute("address", address);
        model.addAttribute("subject", subject);
        model.addAttribute("body", body);

        model.addAttribute("prevIndex", index - 1);
        model.addAttribute("nextIndex", index + 1);
        model.addAttribute("showPrev", index > 0);
        model.addAttribute("showNext", index < arrLength - 1);
    }

}
