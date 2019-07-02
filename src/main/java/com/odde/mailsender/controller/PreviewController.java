package com.odde.mailsender.controller;

import com.odde.mailsender.form.MailSendForm;
import com.odde.mailsender.form.PreviewForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class PreviewController {

    @PostMapping("/preview")
    public String preview(@Valid @ModelAttribute("form") PreviewForm form, BindingResult result, Model model) {

        if (result.hasErrors()) {
            // TODO 後で見直す。
            return "preview";
        }

        return "preview";
    }
}
