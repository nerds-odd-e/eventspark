package com.odde.mailsender.controller;

import com.odde.mailsender.data.RegistrationInfo;
import com.odde.mailsender.form.RegisterForm;
import com.odde.mailsender.service.RegistrationInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    @Autowired
    private RegistrationInfoRepository registrationInfoRepository;

    @PostMapping("/register")
    public String registerToEvent(@ModelAttribute("form") RegisterForm form, BindingResult result, Model model) {
        registrationInfoRepository.save(new RegistrationInfo(form.getFirstName(), form.getLastName(), form.getCompany(), form.getAddress(), "dummy", form.getTicketType(),
                form.getTicketCount(), form.getEventId()));

        return "redirect:/register_complete";
    }

}
