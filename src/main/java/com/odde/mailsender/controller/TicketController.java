package com.odde.mailsender.controller;

import com.odde.mailsender.data.Event;
import com.odde.mailsender.data.Ticket;
import com.odde.mailsender.form.TicketForm;
import com.odde.mailsender.service.EventRepository;
import com.odde.mailsender.service.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class TicketController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping("/owner/event/{name}/ticket")
    public String add(@PathVariable("name") String eventName, Model model) {
        TicketForm form = new TicketForm();
        form.setEventId(eventRepository.findByName(eventName).getId());
        model.addAttribute("form", form);
        setEvent(model, eventName);
        return "add-ticket";
    }

    @PostMapping("/owner/event/{name}/ticket")
    public String addTicket(Model model, @PathVariable("name") String eventName, @ModelAttribute("form") @Valid TicketForm form, BindingResult br) {
        if (br.hasErrors()) {
            setEvent(model, eventName);
            return "add-ticket";
        }
        Ticket ticket = form.createTicket();
        ticketRepository.save(ticket);

        model.addAttribute("form", form);
        model.addAttribute("event", eventRepository.findByName(eventName));
        model.addAttribute("ticket", ticket);
        return "event-detail-owner";
    }

    private void setEvent(Model model, String eventName) {
        Event event = eventRepository.findByName(eventName);
        model.addAttribute("event", event);
    }
}
