package com.odde.mailsender.service;

import com.odde.mailsender.data.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, String> {
}
