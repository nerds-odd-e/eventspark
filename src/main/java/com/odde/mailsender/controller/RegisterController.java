package com.odde.mailsender.controller;

import com.odde.mailsender.data.Event;
import com.odde.mailsender.data.RegistrationInfo;
import com.odde.mailsender.data.Ticket;
import com.odde.mailsender.form.RegisterForm;
import com.odde.mailsender.service.EventRepository;
import com.odde.mailsender.service.RegistrationInfoRepository;
import com.odde.mailsender.service.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class RegisterController {

    @Autowired
    private RegistrationInfoRepository registrationInfoRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @PostMapping("/register")
    public String registerToEvent(@ModelAttribute("form") RegisterForm form, BindingResult result, Model model) {

        if (checkEnableToBuy(form)){
            addFormAndEventAndTicketListToModel(form, model);
            model.addAttribute("errors", "Can't buy.");
            return "register_form";
        }

        registrationInfoRepository.save(RegistrationInfo.builder().firstName(form.getFirstName())
                .lastName(form.getLastName())
                .company(form.getCompany())
                .address(form.getAddress())
                .ticketId(form.getTicketId())
                .ticketCount(form.getTicketCount())
                .eventId(form.getEventId()).build());

        return "redirect:/register_complete";
    }

    public boolean isBuyableForTicketMaximum(int ticketMaximum, Integer ticketSoled, Integer ticketBought) {
        return ticketMaximum >= (ticketSoled + ticketBought);
    }

    private void addFormAndEventAndTicketListToModel(RegisterForm form, Model model) {
        Optional<Event> optionalEvent = eventRepository.findById(form.getEventId());
        Event event = optionalEvent.orElse(null);
        List<Ticket> ticketList = ticketRepository.findByEventId(event.getId());

        model.addAttribute("form", form);
        model.addAttribute("event", event);
        model.addAttribute("ticketList", ticketList);
    }

    private boolean checkEnableToBuy(RegisterForm form){
        Optional<Ticket> optionalTicket = ticketRepository.findById(form.getTicketId());
        Ticket ticket = optionalTicket.orElse(null);

        List<RegistrationInfo> registrationInfoList = registrationInfoRepository.findByTicketId(form.getTicketId());
        Integer soldTicketCount = registrationInfoList.stream().mapToInt(RegistrationInfo::getTicketCount).sum();

        return ticket.getTicketTotal() < form.getTicketCount() + soldTicketCount;
    }
}
