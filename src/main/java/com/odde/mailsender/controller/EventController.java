package com.odde.mailsender.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class EventController {

    @GetMapping("/event/{title}")
    public String showDetail(@PathVariable("title") String title) {
        return "event-detail";
    }

}
