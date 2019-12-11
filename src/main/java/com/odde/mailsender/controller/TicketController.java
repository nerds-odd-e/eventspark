package com.odde.mailsender.controller;

import com.odde.mailsender.data.Event;
import com.odde.mailsender.data.Ticket;
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

//    @PostMapping("/admin/event/{eventName}/ticket")
//    public String addTicket(@PathVariable("eventName") String eventName,
//                            @ModelAttribute("form") TicketForm form, BindingResult result, Model model){
//        addTicketService.addTicket(form);
//        return "event-detail-owner";
//    }

    @PostMapping("/admin/event/{eventName}/ticket")
    public String addTicket(Model model){
        Event event = Event.builder().build();
        Ticket ticket = Ticket.builder()
                .ticketName("ticketName")
                .ticketPrice(1)
                .ticketTotal(1)
                .ticketLimit(1)
                .eventId("1").build();
        model.addAttribute("event", event);
        model.addAttribute("ticket", ticket);
        return "event-detail-owner";
    }
}
