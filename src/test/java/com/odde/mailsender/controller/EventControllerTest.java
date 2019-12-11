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
import java.time.LocalDateTime;
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
                .eventName("ゴスペルワークショップ")
                .location("東京国際フォーラム")
                .createUserName("ゆうこ")
                .createDateTime(currentDateTime)
                .updateDateTime(currentDateTime)
                .summary("ゴスペルワークショップのイベントです。")
                .eventStartDateTime(currentDateTime)
                .eventEndDateTime(currentDateTime)
                .publishedDateTime(currentDateTime)
                .detailText("ゴスペルワークショップ")
                .build();
        eventRepository.insert(event);

        mvc.perform(get("/event/" + event.getEventName()))
                .andExpect(model().attribute("event", event));
    }
}
