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
        Ticket ticket = convertToTicket(ticketForm);
        ticketRepository.save(ticket);
        return ticket;
    }

    private Ticket convertToTicket(TicketForm ticketForm) {
        Ticket ticket = Ticket.builder()
                .ticketName(ticketForm.getTicketName())
                .ticketPrice(ticketForm.getTicketPrice())
                .ticketTotal(ticketForm.getTicketTotal())
                .ticketLimit(ticketForm.getTicketLimit())
                .eventId(ticketForm.getEventId())
                .build();

        return ticket;
    }
}
