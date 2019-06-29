package com.odde.mailsender.controller;

import com.odde.mailsender.data.AddressItem;
import com.odde.mailsender.form.MailSendForm;
import com.odde.mailsender.service.AddressBookService;
import com.odde.mailsender.service.MailInfo;
import com.odde.mailsender.service.MailService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @GetMapping("/send")
    public String send(@ModelAttribute("form") MailSendForm form, BindingResult result, Model model) {
        return "send";
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public String sendEmail(@Valid @ModelAttribute("form") MailSendForm form, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "send";
        }

        try {
            if (!form.isTemplate()) {
                mailService.sendMultiple(Arrays.stream(form.getAddresses()).map(form::createNormalMail).collect(Collectors.toList()));
                return "redirect:/send";
            }

            List<MailInfo> mails = new ArrayList<>();
            for (String address : form.getAddresses()) {
                if (contactNameExists(addressBookService.findByAddress(address)))
                    throw new Exception("When you use template, choose email from contract list that has a name");

                mails.add(form.createRenderedMail(addressBookService.findByAddress(address)));
            }
            mailService.sendMultiple(mails);
            return "redirect:/send";
        } catch (Exception e) {
            result.rejectValue("", "", e.getMessage());
            return "send";
        }
    }

    private boolean contactNameExists(AddressItem addressItem) {
        return addressItem == null || StringUtils.isEmpty(addressItem.getName());
    }

}
