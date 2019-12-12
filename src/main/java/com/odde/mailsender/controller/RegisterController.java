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

import javax.xml.transform.OutputKeys;

@Controller
public class RegisterController {

    @Autowired
    private RegistrationInfoRepository registrationInfoRepository;

    @PostMapping("/register")
    public String registerToEvent(@ModelAttribute("form") RegisterForm form, BindingResult result, Model model) {

        //チケット全体の購入数の上限チェックしたい

        //１人あたりのチケット購入上限チェックしたい

        //OKだったら参加登録可能
        registrationInfoRepository.save(new RegistrationInfo(form.getFirstName(), form.getLastName(), form.getCompany(), form.getAddress(), form.getTicketType(),
                form.getTicketCount(), form.getEventId()));

        return "redirect:/register_complete";
    }

    public boolean isBuyableForTicketMaximum(int ticketMaximum, Integer ticketSoled, Integer ticketBought)
    {
        return ticketMaximum >= (ticketSoled + ticketBought);
    }

}
