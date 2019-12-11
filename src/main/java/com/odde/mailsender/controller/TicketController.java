package com.odde.mailsender.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TicketController {

    @GetMapping("/admin/event/{name}/ticket")
    public String add(@PathVariable("name") String eventName) {
        return "add-ticket";
    }
}
