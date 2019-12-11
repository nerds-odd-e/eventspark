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

    @GetMapping("/event/{title}")
    public String showDetail(@PathVariable("title") String title, Model model) {
        Event event = eventRepository.findByEventName(title);
        model.addAttribute("event", event);
        return "event-detail";
    }

    @GetMapping("/admin/event/{eventName}")
    public String showPreview(@PathVariable("eventName") String eventName, Model model) {
        Event event = eventRepository.findByEventName(eventName);
        model.addAttribute("event", event);
        return "event-preview";
    }
}
