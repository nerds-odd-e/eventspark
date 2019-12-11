package com.odde.mailsender.controller;

import com.odde.mailsender.data.Event;
import com.odde.mailsender.data.Ticket;
import com.odde.mailsender.form.AddEventForm;
import com.odde.mailsender.form.TicketForm;
import com.odde.mailsender.service.AddTicketService;
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

import java.time.LocalDateTime;

@Controller
public class TicketController {
    @Autowired
    private AddTicketService addTicketService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private  TicketRepository ticketRepository;

    @GetMapping("/admin/event/{name}/ticket")
    public String add(@PathVariable("name") String eventName) {
        return "add-ticket";
    }

    @PostMapping("/admin/event/{name}/ticket")
    public String addTicket(Model model, @PathVariable("name") String eventName, @ModelAttribute("form") TicketForm form){
        Ticket ticket = Ticket.builder()
                .ticketName(form.getTicketName())
                .ticketPrice(form.getTicketPrice())
                .ticketTotal(form.getTicketTotal())
                .ticketLimit(form.getTicketLimit())
                .eventId(form.getEventId()).build();

        Event event = eventRepository.findByName(eventName);
        model.addAttribute("event", event);
        model.addAttribute("ticket", ticket);
        return "event-detail-owner";
    }
}
