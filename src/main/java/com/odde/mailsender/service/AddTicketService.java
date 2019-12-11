package com.odde.mailsender.service;

import com.odde.mailsender.data.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddTicketService {
    @Autowired
    private TicketRepository ticketRepository;

    public void addTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }
}
