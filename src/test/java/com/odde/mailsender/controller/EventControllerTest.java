package com.odde.mailsender.controller;

import com.odde.mailsender.data.Event;
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
import org.springframework.test.web.servlet.MvcResult;
import org.thymeleaf.spring5.expression.Mvc;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class EventControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private EventRepository eventRepository;

    @Before
    public void setup() {
        eventRepository.deleteAll();
    }

    @Test
    public void displayEventDetail() throws Exception {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Event event = Event.builder()
                .name("ゴスペルワークショップ")
                .location("東京国際フォーラム")
                .owner("ゆうこ")
                .createDateTime(currentDateTime)
                .updateDateTime(currentDateTime)
                .summary("ゴスペルワークショップのイベントです。")
                .startDateTime(currentDateTime)
                .endDateTime(currentDateTime)
                .publishedDateTime(currentDateTime)
                .detailText("ゴスペルワークショップ")
                .build();
        eventRepository.insert(event);

        mvc.perform(get("/event/" + event.getName()))
                .andExpect(model().attribute("event", event));
    }

    @Test
    public void displayAddEventPage() throws Exception {
        MvcResult result = mvc.perform(get("/owner/event/new")).andReturn();
        assertEquals(200,result.getResponse().getStatus());
    }

    @Test
    public void newEvent() throws Exception {
        MvcResult result = mvc.perform(post("/owner/event")
                .param("name","ゴスペルワークショップ")
                .param("location", "東京フォーラム")
                .param("summary", "イベントのサマリー")
                .param("event_detail","アーティスト：カークフランクリン ¥n 演目：未定")
                .param("event_start_date","2019-12-20 09:00")
                .param("event_end_date","2019-12-21 10:00")
        ).andReturn();
        assertEquals(200,result.getResponse().getStatus());
        Event event=eventRepository.findByName("ゴスペルワークショップ");
        assertNotNull(event);
        assertEquals("ゴスペルワークショップ",event.getName());
    }

}
