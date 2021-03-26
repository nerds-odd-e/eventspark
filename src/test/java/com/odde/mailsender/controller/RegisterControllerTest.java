package com.odde.mailsender.controller;

import com.odde.mailsender.data.Event;
import com.odde.mailsender.data.RegistrationInfo;
import com.odde.mailsender.data.Ticket;
import com.odde.mailsender.service.EventRepository;
import com.odde.mailsender.service.RegistrationInfoRepository;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
public class RegisterControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private RegistrationInfoRepository registrationInfoRepository;

    @Autowired
    private RegisterController registerController;

    @Before
    public void setup() {
        registrationInfoRepository.deleteAll();
        eventRepository.deleteAll();
        ticketRepository.deleteAll();

        eventRepository.save(Event.builder().id("1").name("TestEvent").build());
        eventRepository.save(Event.builder().id("2").name("FailedTestEvent").build());

        ticketRepository.save(Ticket.builder().id("1").ticketName("1day").ticketPrice(1000L).ticketTotal(100L).ticketLimit(10).eventId("1").build());
        ticketRepository.save(Ticket.builder().id("2").ticketName("2days").ticketPrice(1800L).ticketTotal(100L).ticketLimit(10).eventId("1").build());
        ticketRepository.save(Ticket.builder().id("3").ticketName("1day").ticketPrice(1800L).ticketTotal(10L).ticketLimit(10).eventId("2").build());
    }

    @Test
    public void registerToEvent() throws Exception {
        mvc.perform(post("/register")
                .param("firstName", "firstName")
                .param("lastName", "lastName")
                .param("company", "companyName")
                .param("address", "aaa@example.com")
                .param("ticketId", "1")
                .param("eventId", "1"))
                .andExpect(flash().attribute("successMessage", "Complete buy."))
                .andExpect(redirectedUrl("/event/TestEvent"));

        List<RegistrationInfo> all = registrationInfoRepository.findAll();

        assertEquals(1, all.size());
        assertEquals("firstName", all.get(0).getFirstName());
        assertEquals("lastName", all.get(0).getLastName());
        assertEquals("companyName", all.get(0).getCompany());
        assertEquals("aaa@example.com", all.get(0).getAddress());
        assertEquals("1", all.get(0).getTicketId());
        assertEquals("1", all.get(0).getEventId());
        assertNotNull(all.get(0).getId());
    }

    @Test
    public void チケット数が100分の99枚の場合1枚購入出来る() {
        assertTrue(registerController.isBuyableForTicketMaximum(100, 99, 1));
    }

    @Test
    public void チケット数が100分の100枚の場合1枚購入出来ない() {
        assertFalse(registerController.isBuyableForTicketMaximum(100, 100, 1));
    }

    @Test
    public void ticketTotalOver() throws Exception {
        Ticket ticket = Ticket.builder()
                .id("6059a3527818810498ce8deb")
                .ticketName("ATicketName")
                .ticketLimit(1)
                .ticketPrice(1000L)
                .ticketTotal(2L)
                .eventId("605a9ad932d2ef4627b89d90")
                .build();

        ticketRepository.save(ticket);
        eventRepository.save(Event.builder().id("605a9ad932d2ef4627b89d90").build());
        registrationInfoRepository.save(
                RegistrationInfo.builder().firstName("firstName").lastName("lastName").company("companyName")
                        .address("test@example").attendeeFirstName("Attendee").attendeeLastName("NameDesu")
                        .attendeeCompany("Attendee.corp").attendeeAddress("attendee@example.com")
                        .ticketId("6059a3527818810498ce8deb").ticketCount(10).eventId("605a9ad932d2ef4627b89d90").build()
        );

/*
        mvc.perform(post("/register")
                .param("firstName", "firstName")
                .param("lastName", "lastName")
                .param("company", "companyName")
                .param("address", "aaa@example.com")
                .param("ticketId", "3")
                .param("ticketCount", "1")
                .param("eventId", "2"))
                .andExpect(view().name("register_form"))
                .andExpect(model().attribute("errors", "Can't buy."));
                */
        System.out.println("------>");
        System.out.println(mvc);
        System.out.println("<------");

        MockHttpServletRequestBuilder mhsrb = post("/register")
          .param("buyer.firstName", "firstName")
          .param("buyer.lastName", "lastName")
          .param("buyer.company", "companyName")
          .param("buyer.address", "aaa@example.com")
          .param("attendee.firstName", "Attendee")
          .param("attendee.lastName", "NameDesu")
          .param("attendee.company", "Attendee.corp")
          .param("attendee.address", "attendee@example.com")
          .param("ticketId", "6059a3527818810498ce8deb")
          .param("ticketCount", "1")
          .param("eventId", "605a9ad932d2ef4627b89d90");

        ResultActions ra =  mvc.perform(mhsrb);

//        ra.andExpect(MockMvcResultMatchers.model().attribute("errors", "Can't buy."))
//                .andExpect(MockMvcResultMatchers.model().attribute("ticketList", Collections.singletonList(ticket)))
//                .andExpect(model().attribute("form", hasProperty("buyer", hasProperty("firstName", is("firstName")))))
//                .andExpect(model().attribute("form", hasProperty("buyer", hasProperty("lastName", is("lastName")))))
//                .andExpect(model().attribute("form", hasProperty("buyer", hasProperty("company", is("companyName")))))
//                .andExpect(model().attribute("form", hasProperty("buyer", hasProperty("address", is("aaa@example.com")))))
//                .andExpect(model().attribute("form", hasProperty("attendee", hasProperty("firstName", is("Attendee")))))
//                .andExpect(model().attribute("form", hasProperty("attendee", hasProperty("lastName", is("NameDesu")))))
//                .andExpect(model().attribute("form", hasProperty("attendee", hasProperty("company", is("Attendee.corp")))))
//                .andExpect(model().attribute("form", hasProperty("attendee", hasProperty("address", is("attendee@example.com")))))
//                .andExpect(model().attribute("form", hasProperty("ticketId", is("6059a3527818810498ce8deb"))))
//                .andExpect(model().attribute("form", hasProperty("eventId", is("605a9ad932d2ef4627b89d90"))))
//                .andExpect(model().attribute("event", Event.builder().name("TestEvent").id("605a9ad932d2ef4627b89d90").build()))
//                .andExpect(view().name("register_form"));
//        verify(ticketRepository, times(1)).findById("6059a3527818810498ce8deb");

    }
}

