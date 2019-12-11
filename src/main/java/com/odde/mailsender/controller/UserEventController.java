package com.odde.mailsender.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserEventController {

    @GetMapping("/event")
    public String list() {
        return "event-list";
    }
}
