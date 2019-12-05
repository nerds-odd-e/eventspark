package com.odde.mailsender.service;

import com.odde.mailsender.data.Address;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface AddressRepository extends MongoRepository<Address, String> {
    Address findByMailAddress(String mailAddress);

    Address findByNameAndMailAddress(String name, String mailAddress);
}
