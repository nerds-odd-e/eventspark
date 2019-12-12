package com.odde.mailsender.service;

import com.odde.mailsender.data.Ticket;
import com.odde.mailsender.form.TicketForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddTicketService {
    @Autowired
    private TicketRepository ticketRepository;

    public Ticket addTicket(TicketForm ticketForm) {
        return ticketRepository.save(ticketForm.createTicket());
    }
}
