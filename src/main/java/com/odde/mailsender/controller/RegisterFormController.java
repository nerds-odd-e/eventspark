package com.odde.mailsender.controller;

import com.odde.mailsender.data.Event;
import com.odde.mailsender.data.Ticket;
import com.odde.mailsender.form.RegisterForm;
import com.odde.mailsender.service.TicketRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RegisterFormController {

//    private TicketRepository

    @GetMapping("/register_form/{eventName}")
    public String showRegistrationForm(Model model) {
        model.addAttribute("form", new RegisterForm());
        model.addAttribute("event", Event.builder().id("1").name("TestEvent").build());
        List<Ticket> ticketList = new ArrayList<>();
        ticketList.add(Ticket.builder().ticketName("1day").ticketPrice(1000L).ticketTotal(100L).ticketLimit(10).eventId("event").build());
        ticketList.add(Ticket.builder().ticketName("2days").ticketPrice(1800L).ticketTotal(100L).ticketLimit(10).eventId("event").build());
        model.addAttribute("ticketList", ticketList);

        return "register_form";
    }

}
