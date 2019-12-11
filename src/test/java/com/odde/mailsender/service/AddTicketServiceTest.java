package com.odde.mailsender.service;

import com.odde.mailsender.data.Ticket;
import com.odde.mailsender.form.TicketForm;
import io.cucumber.java.bs.A;
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
        TicketForm ticketForm = new TicketForm("ticketName", 1L, 1L, 1, "1");
        // when
        addTicketService.addTicket(ticketForm);
        // then
        int ticketCount = ticketRepository.findAll().size();
        assertEquals(ticketCount, 1);
    }

//    @Test
//    public void TicketCanBeAddedFromFormToTheTicketRespository(){
//        // given
//        TicketForm ticketForm = new TicketForm("ticketName", 1L, 1L, 1, "1");
//        // when
//        Ticket ticket = addTicketService.convertToTicket(ticketForm);
//        // then
//        Ticket expected = new Ticket("ticketName", 1L, 1L, 1, "1");
//        assertThat(ticket.getId(), is(expected.getId()));
//    }

}
