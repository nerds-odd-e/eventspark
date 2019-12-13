package com.odde.mailsender.controller;

import com.odde.mailsender.data.Event;
import com.odde.mailsender.data.Ticket;
import com.odde.mailsender.service.EventRepository;
import com.odde.mailsender.service.TicketRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
public class RegistrationFormControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TicketRepository ticketRepository;

    private List<Ticket> expectedTicketList;

    @Before
    public void setUp() {
        eventRepository.deleteAll();
        ticketRepository.deleteAll();

        eventRepository.save(Event.builder().id("1").name("TestEvent").startDateTime(LocalDateTime.of(2020,6,15,13,0))
                .endDateTime(LocalDateTime.of(2020,6,16,17,0)).location("秋葉原").build());
        eventRepository.save(Event.builder().id("2").name("TestEvent2").startDateTime(LocalDateTime.of(2020,6,15,13,0))
                .endDateTime(LocalDateTime.of(2020,6,16,17,0)).location("秋葉原").build());

        Ticket ticket1 = ticketRepository.save(Ticket.builder().ticketName("1day").ticketPrice(1000L).ticketTotal(100L).ticketLimit(10).eventId("1").build());
        Ticket ticket2 = ticketRepository.save(Ticket.builder().ticketName("2days").ticketPrice(1800L).ticketTotal(100L).ticketLimit(10).eventId("1").build());
        expectedTicketList = new ArrayList<>();
        expectedTicketList.add(ticket1);
        expectedTicketList.add(ticket2);
    }

    @Test
    public void showRegistrationForm() throws Exception {

        mvc.perform(get("/register_form/TestEvent"))
                .andExpect(view().name("register_form"))
                .andExpect(model().attribute("event", allOf(
                        hasProperty("name", is("TestEvent")),
                        is(not(nullValue()))
                )))
                .andExpect(model().attribute("event", Event.builder().id("1").name("TestEvent").startDateTime(LocalDateTime.of(2020,6,15,13,0))
                        .endDateTime(LocalDateTime.of(2020,6,16,17,0)).location("秋葉原").build()))
                .andExpect(model().attribute("ticketList", expectedTicketList));
    }

}
