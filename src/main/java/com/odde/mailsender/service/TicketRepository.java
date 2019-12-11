package com.odde.mailsender.service;

import com.odde.mailsender.data.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TicketRepository extends MongoRepository<Ticket, String> {
}
