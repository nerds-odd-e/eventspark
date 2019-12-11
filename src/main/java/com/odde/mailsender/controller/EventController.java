package com.odde.mailsender.controller;

import com.odde.mailsender.data.Event;
import com.odde.mailsender.service.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class EventController {

    @Autowired
    private EventRepository eventRepository;
    @GetMapping("/event/{eventName}")
    public String showDetail(@PathVariable("eventName") String eventName, Model model) {

        Event event = eventRepository.findByEventName(eventName);
        model.addAttribute("event", event);
        return "event-detail";
    }

    @GetMapping("/owner/event/{eventName}")
    public String showDetailForOwner(@PathVariable("eventName") String eventName, Model model) {
        Event event = eventRepository.findByEventName(eventName);
        model.addAttribute("event", event);
        return "event-detail-owner";
    }
}
