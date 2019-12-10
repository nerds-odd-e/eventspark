package com.odde.mailsender.service;

import com.odde.mailsender.data.RegistrationInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RegistrationInfoRepository extends MongoRepository<RegistrationInfo, String> {
}
