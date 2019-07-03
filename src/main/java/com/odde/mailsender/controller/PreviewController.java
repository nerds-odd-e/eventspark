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
    private MailService mailService;
    @Autowired
    private AddressBookService addressBookService;

    @PostMapping("/preview")
    public String preview(@Valid @ModelAttribute("form") MailSendForm form, BindingResult result, Model model) {

        if (result.hasErrors()) {
            // TODO 後で見直す。
            return "preview";
        }


        if(form.isTemplate()) {
            MailInfo info = form.createRenderedMail(addressBookService.findByAddress(form.getAddress()));
            model.addAttribute("address", info.getTo());
            model.addAttribute("subject", info.getSubject());
            model.addAttribute("body", info.getBody());
        } else {
            model.addAttribute("address", form.getAddress());
            model.addAttribute("subject", form.getSubject());
            model.addAttribute("body", form.getBody());
        }


        try {
            //mailService.preview(form.createPreviewInfo(null));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "preview";
    }
}
