package com.odde.mailsender.controller;

import com.odde.mailsender.data.Event;
import com.odde.mailsender.data.Ticket;
import com.odde.mailsender.form.RegisterForm;
import com.odde.mailsender.service.EventRepository;
import com.odde.mailsender.service.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class RegisterFormController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping("/register_form/{eventName}")
    public String showRegistrationForm(@PathVariable("eventName") String eventName, Model model) {
        Event event = eventRepository.findByName(eventName);
        List<Ticket> ticketList = ticketRepository.findByEventId(event.getId());

        model.addAttribute("form", new RegisterForm());
        model.addAttribute("event", event);
        model.addAttribute("ticketList", ticketList);

        return "register_form";
    }

}
