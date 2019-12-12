package com.odde.mailsender.controller;

import com.odde.mailsender.data.Event;
import com.odde.mailsender.form.AddEventForm;
import com.odde.mailsender.service.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/event/{eventName}")
    public String showDetail(@PathVariable("eventName") String eventName, Model model) {

        Event event = eventRepository.findByName(eventName);
        model.addAttribute("event", event);
        return "event-detail";
    }

    @GetMapping("/owner/event/{eventName}")
    public String showDetailForOwner(@PathVariable("eventName") String eventName, Model model) {
        Event event = eventRepository.findByName(eventName);
        model.addAttribute("event", event);
        return "event-detail-owner";
    }

    @GetMapping("/owner/event/new")
    public String AddEvent() {
        return "event-new";
    }

    @PostMapping("/owner/event")
    public String addEvent(@Valid @ModelAttribute("form") AddEventForm form, BindingResult result, Model model) {
        Event event = form.createEvent();
        eventRepository.insert(event);
        model.addAttribute("event", event);
        return "event-detail-owner";

    }

}
