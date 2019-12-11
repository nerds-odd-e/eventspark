package com.odde.mailsender.service;

import com.odde.mailsender.data.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TicketRepository extends MongoRepository<Ticket, String> {
    List<Ticket> findByEventId(String eventId);
}
