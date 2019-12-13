package com.odde.mailsender.service;

import com.odde.mailsender.data.RegistrationInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RegistrationInfoRepository extends MongoRepository<RegistrationInfo, String> {
    List<RegistrationInfo> findByEventId(String id);

    List<RegistrationInfo> findByTicketId(String ticketId);
}
