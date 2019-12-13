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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
public class TicketController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping("/owner/event/{name}/ticket")
    public String add(@PathVariable("name") String eventName, Model model) {
        Event event = eventRepository.findByName(eventName);
        model.addAttribute("form", new TicketForm(event.getId()));
        model.addAttribute("event", event);
        return "add-ticket";
    }

    @PostMapping("/owner/event/{name}/ticket")
    public String addTicket(Model model, @PathVariable("name") String eventName, @ModelAttribute("form") @Valid TicketForm form, BindingResult br) throws UnsupportedEncodingException {
        if (br.hasErrors()) {
            model.addAttribute("event", eventRepository.findByName(eventName));
            return "add-ticket";
        }
        Ticket ticket = form.createTicket();
        ticketRepository.save(ticket);
        return "redirect:/owner/event/" + URLEncoder.encode(eventName, "UTF-8");
    }

}
