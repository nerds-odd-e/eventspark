package com.odde.mailsender.controller;

import com.odde.mailsender.data.Event;
import com.odde.mailsender.data.Ticket;
import com.odde.mailsender.form.TicketForm;
import com.odde.mailsender.service.EventRepository;
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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class TicketControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventRepository eventRepository;

    @Before
    public void setup() {
        eventRepository.deleteAll();
    }

    @Before
    public void createEvent() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Event eventEntity = Event.builder()
                .name("ゴスペルワークショップ")
                .location("東京国際フォーラム")
                .owner("ゆうこ")
                .createDateTime(currentDateTime)
                .updateDateTime(currentDateTime)
                .summary("ゴスペルワークショップのイベントです。")
                .startDateTime(currentDateTime)
                .endDateTime(currentDateTime)
                .publishedDateTime(currentDateTime)
                .detail("ゴスペルワークショップ")
                .build();
        eventRepository.insert(eventEntity);
    }

    @Test
    public void getIndexHtmlPage() {
        String ret = new TicketController().add("test");
        assertEquals("add-ticket", ret);
    }

    @Test
    public void canAddTicketFromAddTicketPageAndRedirectToEventPage() throws Exception{
        //given
        TicketForm ticketForm = new TicketForm("ticketName", 1L, 1L, 1, "1");

        Ticket ticket = Ticket.builder()
                .ticketName(ticketForm.getTicketName())
                .ticketPrice(ticketForm.getTicketPrice())
                .ticketTotal(ticketForm.getTicketTotal())
                .ticketLimit(ticketForm.getTicketLimit())
                .eventId(ticketForm.getEventId())
                .build();

        Event event = eventRepository.findByName("ゴスペルワークショップ");
        //when
         mockMvc.perform(post("/admin/event/ゴスペルワークショップ/ticket")
            .param("ticketName", ticketForm.getTicketName())
            .param("ticketPrice", String.valueOf(ticketForm.getTicketPrice()))
            .param("ticketTotal", String.valueOf(ticketForm.getTicketTotal()))
            .param("ticketLimit", String.valueOf(ticketForm.getTicketLimit()))
             .param("eventId", ticketForm.getEventId()))
                 .andExpect(model().attribute("ticket", ticket))
                 .andExpect(model().attribute("event", event))
                     .andExpect(view().name("event-detail-owner"));
    }
}