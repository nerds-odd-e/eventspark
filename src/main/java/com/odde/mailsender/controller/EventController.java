package com.odde.mailsender.controller;

import com.odde.mailsender.data.Event;
import com.odde.mailsender.data.RegistrationInfo;
import com.odde.mailsender.data.Ticket;
import com.odde.mailsender.form.AddEventForm;
import com.odde.mailsender.service.EventRepository;
import com.odde.mailsender.service.RegistrationInfoRepository;
import com.odde.mailsender.service.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private RegistrationInfoRepository registrationInfoRepository;

    @GetMapping("/event/{eventName}")
    public String showDetail(@PathVariable("eventName") String eventName, Model model) {
        Event event = eventRepository.findByName(eventName);
        model.addAttribute("event", event);

        List<Ticket> ticketList = ticketRepository.findByEventId(event.getId());
        model.addAttribute("ticketList", ticketList);

        List<Long> unsoldList = new ArrayList<>();
        for (Ticket ticket : ticketList) {
            List<RegistrationInfo> registrationInfoList = registrationInfoRepository.findByEventId(event.getId());
            long sold = 0;
            for (RegistrationInfo registrationInfo : registrationInfoList) {
                sold += registrationInfo.getTicketCount();
            }
            long unsold = ticket.getTicketTotal() - sold;
            unsoldList.add(unsold);
        }
        model.addAttribute("unsoldList", unsoldList);
        model.addAttribute("ticketList", ticketList);
        return "event-detail";
    }

    @GetMapping("/owner/event/{eventName}")
    public String showDetailForOwner(@PathVariable("eventName") String eventName, Model model) {
        Event event = eventRepository.findByName(eventName);
        model.addAttribute("event", event);
        // TODO: [BAU team] ticketが存在する場合は、return ticket
        model.addAttribute("ticket", null);
        return "event-detail-owner";
    }

    @GetMapping("/owner/event/new")
    public String AddEvent(AddEventForm form, Model model) {
        model.addAttribute("form",form);
        return "event-new";
    }

    @PostMapping("/owner/event")
    public String addEvent(@Valid @ModelAttribute("form") AddEventForm form, BindingResult result, Model model) {
        Event event = eventRepository.findByName(form.getName());
        if(event != null){
            model.addAttribute("form", form);
            model.addAttribute("errorMessage", "Failed!: Same name event already exist.");
            return "event-new";
        }
        event = form.createEvent();
        event.setDetailUrl("http://localhost:8080/event/"+event.getName());

        eventRepository.insert(event);
        model.addAttribute("event", event);
        model.addAttribute("ticket", null);
        return "event-detail-owner";

    }

}
