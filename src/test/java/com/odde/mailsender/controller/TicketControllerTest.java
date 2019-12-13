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

import java.net.URLEncoder;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    public void canAddTicketFromAddTicketPageAndRedirectToEventPage() throws Exception {
        //given
        TicketForm ticketForm = new TicketForm("ticketName", 1L, 1L, 1, "1");

        //when
        mockMvc.perform(post("/owner/event/ゴスペルワークショップ/ticket")
                .param("ticketName", ticketForm.getTicketName())
                .param("ticketPrice", String.valueOf(ticketForm.getTicketPrice()))
                .param("ticketTotal", String.valueOf(ticketForm.getTicketTotal()))
                .param("ticketLimit", String.valueOf(ticketForm.getTicketLimit()))
                .param("eventId", ticketForm.getEventId()))
                .andExpect(redirectedUrl("/owner/event/" + URLEncoder.encode("ゴスペルワークショップ", "UTF-8")));
    }

    @Test
    public void inputNoneOutputErrorMessage() throws Exception {
        mockMvc.perform(post("/owner/event/ゴスペルワークショップ/ticket")
                .param("ticketName", "")
                .param("ticketPrice", "")
                .param("ticketTotal", "")
                .param("ticketLimit", "")
                .param("eventId", ""))
                .andExpect(content().string(containsString("チケット名を入力してください")))
                .andExpect(content().string(containsString("金額を入力してください")))
                .andExpect(content().string(containsString("枚数を入力してください")))
                .andExpect(content().string(containsString("一人当たりの上限数を入力してください")))
        ;
    }
}