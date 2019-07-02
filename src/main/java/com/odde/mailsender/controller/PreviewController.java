package com.odde.mailsender.controller;

import com.odde.mailsender.form.MailSendForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PreviewController {

    @PostMapping("/preview")
    public String preview(@ModelAttribute("form") MailSendForm form, BindingResult result, Model model) {
        return "preview";
    }
}
