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

import java.util.ArrayList;
import java.util.List;

@Controller
public class RegisterFormController {

    @Autowired
    private EventRepository eventRepository;
//    private TicketRepository

    @GetMapping("/register_form/{eventName}")
    public String showRegistrationForm(@PathVariable("eventName") String eventName, Model model) {
        Event event = eventRepository.findByName(eventName);
//        if (event == null){
//            throw new IllegalArgumentException("Event Name " + eventName + " not found");
//        }


        model.addAttribute("form", new RegisterForm());
//        model.addAttribute("event", Event.builder().id("1").name("TestEvent").build());
        model.addAttribute("event", event);
        List<Ticket> ticketList = new ArrayList<>();
        ticketList.add(Ticket.builder().ticketName("1day").ticketPrice(1000L).ticketTotal(100L).ticketLimit(10).eventId("event").build());
        ticketList.add(Ticket.builder().ticketName("2days").ticketPrice(1800L).ticketTotal(100L).ticketLimit(10).eventId("event").build());
        model.addAttribute("ticketList", ticketList);

        return "register_form";
    }

}
