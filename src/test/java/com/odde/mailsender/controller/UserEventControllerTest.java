package com.odde.mailsender.controller;

import com.odde.mailsender.bean.UserEventListBean;
import com.odde.mailsender.data.Event;
import com.odde.mailsender.service.EventRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
public class UserEventControllerTest {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserEventController userEventController;

    @Test
    public void イベントデータがないときにイベントが空になる() {
        eventRepository.deleteAll();
        UserEventListBean bean = userEventController.getUserEventListBean();
        Assert.assertEquals(0, bean.getEventList().size());

    }

    @Test
    public void イベントデータが２件のときにイベントが２個表示される() {
        eventRepository.deleteAll();

        LocalDateTime currentDateTime = LocalDateTime.now();
        Stream.of("1", "2").forEach(id -> {
            Event event = Event.builder()
                    .id(id)
                    .name("ゴスペルワークショップ" + id)
                    .location("東京国際フォーラム")
                    .owner("ゆうこ")
                    .createDateTime(currentDateTime)
                    .updateDateTime(currentDateTime)
                    .summary("ゴスペルワークショップのイベントです。")
                    .startDateTime(currentDateTime)
                    .endDateTime(currentDateTime)
                    .publishedDateTime(currentDateTime)
                    .build();
            eventRepository.insert(event);
        });
        UserEventListBean bean = userEventController.getUserEventListBean();
        Assert.assertEquals(2, bean.getEventList().size());

    }

}

