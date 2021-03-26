package com.odde.mailsender.controller;

import com.odde.mailsender.data.Event;
import com.odde.mailsender.data.RegistrationInfo;
import com.odde.mailsender.data.Ticket;
import com.odde.mailsender.form.RegisterForm;
import com.odde.mailsender.service.EventRepository;
import com.odde.mailsender.service.RegistrationInfoRepository;
import com.odde.mailsender.service.TicketRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
public class RegisterControllerMockTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EventRepository eventRepository;

    @MockBean
    private TicketRepository ticketRepository;

    @MockBean
    private RegistrationInfoRepository registrationInfoRepository;

    @Before
    public void setup() {

        when(ticketRepository.findById("6059a3527818810498ce8deb")).thenReturn(Optional.of(Ticket.builder()
                                                                                                 .id("6059a3527818810498ce8deb")
                                                                                                 .ticketName("ATicketName")
                                                                                                 .ticketLimit(3)
                                                                                                 .ticketPrice(1000L)
                                                                                                 .ticketTotal(10L)
                                                                                                 .eventId("605a9ad932d2ef4627b89d90")
                                                                                                 .build()));

        when(registrationInfoRepository.findByTicketId("6059a3527818810498ce8deb"))
          .thenReturn(Arrays.asList(RegistrationInfo.builder()
                                                    .id("605a9afc32d2ef4627b89d92")
                                                    .firstName("Taiki")
                                                    .lastName("Kawaji")
                                                    .company("ABC")
                                                    .address("kawaji@fake.com")
                                                    .ticketId("6059a3527818810498ce8deb")
                                                    .ticketCount(2)
                                                    .eventId("605a9ad932d2ef4627b89d90")
                                                    .build(),
                                    RegistrationInfo.builder()
                                                    .id("605a9afc32d2ef4627b89d91")
                                                    .firstName("Yuta")
                                                    .lastName("Sasaki")
                                                    .company("ABC")
                                                    .address("sasaki@fake.com")
                                                    .ticketId("6059a3527818810498ce8deb")
                                                    .ticketCount(3)
                                                    .eventId("605a9ad932d2ef4627b89d90")
                                                    .build(),
                                    RegistrationInfo.builder()
                                                    .id("605a9afc32d2ef4627b89d91")
                                                    .firstName("Yuta")
                                                    .lastName("Sasaki")
                                                    .company("ABC")
                                                    .address("sasaki@fake.com")
                                                    .ticketId("6059a3527818810498ce8deb")
                                                    .ticketCount(2)
                                                    .eventId("605a9ad932d2ef4627b89d90")
                                                    .build()));

        when(eventRepository.findById("605a9ad932d2ef4627b89d90")).thenReturn(Optional.of(Event.builder()
                                                                                               .name("TestEvent")
                                                                                               .id("605a9ad932d2ef4627b89d90")
                                                                                               .build()));
    }
//
//    @Test
//    public void 正常にレジストレーションができる場合() throws Exception {
//
//        mvc.perform(post("/register")
//                .param("buyer.firstName", "firstName")
//                .param("buyer.lastName", "lastName")
//                .param("buyer.company", "companyName")
//                .param("buyer.address", "aaa@example.com")
//                .param("attendee.firstName", "Attendee")
//                .param("attendee.lastName", "NameDesu")
//                .param("attendee.company", "Attendee.corp")
//                .param("attendee.address", "attendee@example.com")
//                .param("ticketId", "6059a3527818810498ce8deb")
//                .param("eventId", "605a9ad932d2ef4627b89d90"))
//                .andExpect(flash().attribute("successMessage", "Complete buy."))
//                .andExpect(redirectedUrl("/event/TestEvent"));
//
//
//        verify(ticketRepository, times(1)).findById("6059a3527818810498ce8deb");
//        verify(registrationInfoRepository, times(1)).findByTicketId("6059a3527818810498ce8deb");
//        verify(eventRepository, times(1)).findById("605a9ad932d2ef4627b89d90");
//        verify(registrationInfoRepository, times(1)).save(RegistrationInfo.builder()
//                                                                                                  .firstName("firstName")
//                                                                                                  .lastName("lastName")
//                                                                                                  .company("companyName")
//                                                                                                  .address("aaa@example.com")
//                                                                                                  .attendeeFirstName("Attendee")
//                                                                                                  .attendeeLastName("NameDesu")
//                                                                                                  .attendeeCompany("Attendee.corp")
//                                                                                                  .attendeeAddress("attendee@example.com")
//                                                                                                  .ticketId("6059a3527818810498ce8deb")
//                                                                                                  .ticketCount(1)
//                                                                                                  .eventId("605a9ad932d2ef4627b89d90")
//                                                                                                  .build());
//    }
//
//    @Test
//    public void チケットの枚数が足りない場合() throws Exception {
//        Ticket ticket = Ticket.builder()
//                               .id("6059a3527818810498ce8deb")
//                               .ticketName("ATicketName")
//                               .ticketLimit(3)
//                               .ticketPrice(1000L)
//                               .ticketTotal(7L)
//                               .eventId("605a9ad932d2ef4627b89d90")
//                               .build();
//
//        when(ticketRepository.findById("6059a3527818810498ce8deb")).thenReturn(Optional.of(ticket));
//        when(ticketRepository.findByEventId("605a9ad932d2ef4627b89d90")).thenReturn(
//          Collections.singletonList(ticket));
//
//        mvc.perform(post("/register")
//          .param("buyer.firstName", "firstName")
//          .param("buyer.lastName", "lastName")
//          .param("buyer.company", "companyName")
//          .param("buyer.address", "aaa@example.com")
//          .param("attendee.firstName", "Attendee")
//          .param("attendee.lastName", "NameDesu")
//          .param("attendee.company", "Attendee.corp")
//          .param("attendee.address", "attendee@example.com")
//          .param("ticketId", "6059a3527818810498ce8deb")
//          .param("eventId", "605a9ad932d2ef4627b89d90"))
//          .andExpect(MockMvcResultMatchers.model().attribute("errors", "Can't buy."))
//          .andExpect(MockMvcResultMatchers.model().attribute("ticketList", Collections.singletonList(ticket)))
//          .andExpect(model().attribute("form", hasProperty("buyer", hasProperty("firstName", is("firstName")))))
//          .andExpect(model().attribute("form", hasProperty("buyer", hasProperty("lastName", is("lastName")))))
//          .andExpect(model().attribute("form", hasProperty("buyer", hasProperty("company", is("companyName")))))
//          .andExpect(model().attribute("form", hasProperty("buyer", hasProperty("address", is("aaa@example.com")))))
//          .andExpect(model().attribute("form", hasProperty("attendee", hasProperty("firstName", is("Attendee")))))
//          .andExpect(model().attribute("form", hasProperty("attendee", hasProperty("lastName", is("NameDesu")))))
//          .andExpect(model().attribute("form", hasProperty("attendee", hasProperty("company", is("Attendee.corp")))))
//          .andExpect(model().attribute("form", hasProperty("attendee", hasProperty("address", is("attendee@example.com")))))
//          .andExpect(model().attribute("form", hasProperty("ticketId", is("6059a3527818810498ce8deb"))))
//          .andExpect(model().attribute("form", hasProperty("eventId", is("605a9ad932d2ef4627b89d90"))))
//          .andExpect(model().attribute("event", Event.builder().name("TestEvent").id("605a9ad932d2ef4627b89d90").build()))
//          .andExpect(view().name("register_form"));
//
//        verify(ticketRepository, times(1)).findById("6059a3527818810498ce8deb");
//        verify(registrationInfoRepository, times(1)).findByTicketId("6059a3527818810498ce8deb");
//        verify(eventRepository,times(1)).findById("605a9ad932d2ef4627b89d90");
//    }
}
