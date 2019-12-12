package com.odde.mailsender.controller;

import com.odde.mailsender.bean.UserEventListBean;
import com.odde.mailsender.data.Event;
import com.odde.mailsender.data.RegistrationInfo;
import com.odde.mailsender.data.Ticket;
import com.odde.mailsender.service.EventRepository;
import com.odde.mailsender.service.RegistrationInfoRepository;
import com.odde.mailsender.service.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserEventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private RegistrationInfoRepository registrationInfoRepository;

    @GetMapping("/event")
    public ModelAndView list() {
        Map<String, Object> model = new HashMap<>();
        model.put("bean", getUserEventListBean());
        return new ModelAndView("event-list", model);
    }

    protected UserEventListBean getUserEventListBean() {
        List<Event> eventList = eventRepository.findAll();
        List<Ticket> ticketList = ticketRepository.findAll();
        List<RegistrationInfo> registrationInfoList = registrationInfoRepository.findAll();
        return UserEventListBean.create(eventList, ticketList, registrationInfoList);
    }

}
