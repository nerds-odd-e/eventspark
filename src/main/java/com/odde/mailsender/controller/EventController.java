package com.odde.mailsender.controller;

import com.odde.mailsender.data.Event;
import com.odde.mailsender.data.RegistrationInfo;
import com.odde.mailsender.data.Ticket;
import com.odde.mailsender.exception.HttpStatus404Exception;
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
        if(event == null) throw new HttpStatus404Exception();
        List<Ticket> ticketList = ticketRepository.findByEventId(event.getId());
        List<RegistrationInfo> registrationInfoList = registrationInfoRepository.findByEventId(event.getId());

        model.addAttribute("event", event);
        model.addAttribute("ticketList", ticketList);
        model.addAttribute("unsoldList", event.countUnsoldTickets(ticketList, registrationInfoList));

        return "event-detail";
    }

    @GetMapping("/owner/event/{eventName}")
    public String showDetailForOwner(@PathVariable("eventName") String eventName, Model model) {
        model.addAttribute("event", eventRepository.findByName(eventName));
        model.addAttribute("ticketList", ticketRepository.findByEventId(eventRepository.findByName(eventName).getId()));
        return "event-detail-owner";
    }

    @GetMapping("/owner/event/new")
    public String AddEvent(AddEventForm form, Model model) {
        model.addAttribute("form", form);
        return "event-new";
    }

    @GetMapping("/owner/event/{eventName}/edit")
    public String editEvent(@PathVariable("eventName") String eventName, AddEventForm form, Model model) {
        Event event = eventRepository.findByName(eventName);
        if (event == null) {
            model.addAttribute("errorMessage", "NotFound");
            return "event-list";
        }
        model.addAttribute("event", event);
        return "event-edit";
    }

    @PostMapping("/owner/event")
    public String addEvent(@Valid @ModelAttribute("form") AddEventForm form, BindingResult result, Model model) {
        Event event = eventRepository.findByName(form.getName());
        if (event != null) {
            String errorMessage = "Failed!: Same name event already exist.";
            addAttributeErrorMessage(form, model, errorMessage);
            return "event-new";
        }

        //if (result.hasErrors()) {
        //    String errorMessage = "There is an error in the input contents.";
        //    addAttributeErrorMessage(form, model, errorMessage);
        //    return "event-new";
        //}

        event = form.createEvent();

        eventRepository.insert(event);
        model.addAttribute("event", event);
        model.addAttribute("ticket", null);

        return "event-detail-owner";

    }

    private void addAttributeErrorMessage(AddEventForm form, Model model, String errorMessage) {
        model.addAttribute("form", form);
        model.addAttribute("errorMessage", errorMessage);
    }

    @PutMapping("/owner/event/{eventName}")
    public String updateEvent(@PathVariable("eventName") String eventName,@Valid @ModelAttribute("form") AddEventForm form, BindingResult result, Model model) {
        Event event = eventRepository.findByName(eventName);
        if (event == null) {
            model.addAttribute("errorMessage", "NotFound");
            return "event-list";
        }
        Event updatedEvent = form.createEvent();
        updatedEvent.setId(event.getId());

        eventRepository.save(updatedEvent);
        model.addAttribute("event", updatedEvent);
        return "event-detail-owner";

    }
}
