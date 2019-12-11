package com.odde.mailsender.service;

import com.odde.mailsender.data.Ticket;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class AddTicketServiceTest {
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private AddTicketService addTicketService;

    @Before
    public void setUp() {
        ticketRepository.deleteAll();
    }

    @Test
    public void aTicketCanBeAddedToTheTicketRespository() {
        // given
        Ticket ticket = Ticket.builder()
                .ticketName("ticketName")
                .ticketPrice(1L)
                .ticketTotal(1L)
                .ticketLimit(1)
                .eventId("1")
                .build();
        // when
        addTicketService.addTicket(ticket);
        // then
        int ticketCount = ticketRepository.findAll().size();
        assertEquals(ticketCount, 1);
    }

}
