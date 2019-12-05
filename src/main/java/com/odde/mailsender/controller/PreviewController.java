package com.odde.mailsender.controller;

import com.odde.mailsender.data.Address;
import com.odde.mailsender.form.MailSendForm;
import com.odde.mailsender.service.AddressRepository;
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
    private AddressRepository addressRepository;

    @PostMapping("/preview/{index}")
    public String preview(@Valid @ModelAttribute("form") MailSendForm form, BindingResult result, Model model, @PathVariable int index) {

        if (result.hasErrors()) {
            return "home";
        }

        String[] addressArr = form.getAddresses();
        String address = addressArr[index];

        PreviewNavigation previewNavigation = new PreviewNavigation(index, addressArr.length - 1);

        MailInfo info;
        if(form.isTemplate()) {
            Address a = addressRepository.findByMailAddress(address);
            info = form.createRenderedMail(new Address(a.getName(), a.getMailAddress()));
        } else {
            info = new MailInfo(null, address, form.getSubject(), form.getBody());
        }

        setModelAttributes(model, previewNavigation, info);

        return "preview";
    }

    private void setModelAttributes(Model model, PreviewNavigation previewNavigation, MailInfo mailInfo) {
        model.addAttribute("mailInfo", mailInfo);
        model.addAttribute("previewNavigation", previewNavigation);
    }


}
