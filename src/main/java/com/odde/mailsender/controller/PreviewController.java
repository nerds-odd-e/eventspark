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
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class PreviewController {
    @Autowired
    private AddressBookService addressBookService;

    @PostMapping("/preview")
    public String preview(@Valid @ModelAttribute("form") MailSendForm form, BindingResult result, Model model) {

//        if (result.hasErrors()) {
//            return "preview";
//        }


        if(form.isTemplate()) {
            MailInfo info = form.createRenderedMail(addressBookService.findByAddress(form.getAddress()));
            setModelAttributes(model, info.getTo(), info.getSubject(), info.getBody());
        } else {
            setModelAttributes(model, form.getAddress(), form.getSubject(), form.getBody());
        }

        return "preview";
    }

    private void setModelAttributes(Model model, String address, String subject, String body) {
        model.addAttribute("address", address);
        model.addAttribute("subject", subject);
        model.addAttribute("body", body);
    }

}
