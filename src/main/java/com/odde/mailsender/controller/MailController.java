package com.odde.mailsender.controller;

import com.odde.mailsender.data.AddressItem;
import com.odde.mailsender.form.MailSendForm;
import com.odde.mailsender.service.AddressBookService;
import com.odde.mailsender.service.MailInfo;
import com.odde.mailsender.service.MailService;
import com.odde.mailsender.service.MailTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MailController {

    @Autowired
    private MailService mailService;
    @Autowired
    private AddressBookService addressBookService;

    @GetMapping("")
    public String defaultEndpoint(@ModelAttribute("form") MailSendForm form, BindingResult result, Model model) {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String goToHome(@ModelAttribute("form") MailSendForm form, BindingResult result, Model model) {
        return "home";
    }

    @PostMapping(value = "/home")
    public String goBackToHome(@Valid @ModelAttribute("form") MailSendForm form, BindingResult result, Model model) {
        return "home";
    }

    @PostMapping(value = "/send")
    public String sendEmail(@Valid @ModelAttribute("form") MailSendForm form, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "home";
        }

        try {
            if (!form.isTemplate()) {
                mailService.sendMultiple(Arrays.stream(form.getAddresses()).map(form::createNormalMail).collect(Collectors.toList()));
                return "redirect:/home";
            }

            List<MailInfo> mails = new ArrayList<>();
            for (String address : form.getAddresses()) {
                if (contactNameNotExists(addressBookService.findByAddress(address)))
                    throw new Exception("When you use template, choose email from contract list that has a name");

                mails.add(form.createRenderedMail(addressBookService.findByAddress(address)));
            }
            mailService.sendMultiple(mails);
            return "redirect:/home";
        } catch (Exception e) {
            result.rejectValue("", "", e.getMessage());
            return "home";
        }
    }

    @PostMapping(value = "/load")
    public String loadTemplate(@ModelAttribute("form") MailSendForm form, BindingResult result, Model model){
        MailTemplate template = mailService.getTemplate();

        form.setSubject(template.getSubject());
        form.setBody(template.getBody());
        return "home";
    }


    private boolean contactNameNotExists(AddressItem addressItem) {
        return addressItem == null || StringUtils.isEmpty(addressItem.getName());
    }

}
