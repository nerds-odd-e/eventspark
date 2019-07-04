package com.odde.mailsender.controller;

import com.odde.mailsender.form.MailSendForm;
import com.odde.mailsender.service.AddressBookService;
import com.odde.mailsender.service.MailInfo;
import com.odde.mailsender.service.PreviewNavigation;
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

        PreviewNavigation previewNavigation = new PreviewNavigation(index, addressArr.length - 1);

        if(form.isTemplate()) {
            MailInfo info = form.createRenderedMail(addressBookService.findByAddress(address));
            setModelAttributes(model, previewNavigation, info);
        } else {
            MailInfo info = new MailInfo(null, address, form.getSubject(), form.getBody());
            setModelAttributes(model, previewNavigation, info);
        }

        return "preview";
    }

    private void setModelAttributes(Model model, PreviewNavigation previewNavigation, MailInfo mailInfo) {
        model.addAttribute("mailInfo", mailInfo);

        model.addAttribute("prevIndex", previewNavigation.getPreviousIndex());
        model.addAttribute("nextIndex", previewNavigation.getNextIndex());
        model.addAttribute("showPrev", previewNavigation.canShowPrevious());
        model.addAttribute("showNext", previewNavigation.canShowNext());
    }


}
