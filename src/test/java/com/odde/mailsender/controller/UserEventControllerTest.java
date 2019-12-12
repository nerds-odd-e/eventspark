package com.odde.mailsender.controller;

import com.odde.mailsender.bean.UserEventListBean;
import com.odde.mailsender.data.Event;
import com.odde.mailsender.data.Ticket;
import com.odde.mailsender.service.EventRepository;
import com.odde.mailsender.service.TicketRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
public class UserEventControllerTest {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserEventController userEventController;

    @Autowired
    private MockMvc mvc;

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
            Event event = createEventBuilder(currentDateTime)
                    .id(id)
                    .name("ゴスペルワークショップ" + id)
                    .build();
            eventRepository.insert(event);
        });
        UserEventListBean bean = userEventController.getUserEventListBean();
        Assert.assertEquals(2, bean.getEventList().size());
    }

    @Test
    public void 全ての項目が表示されること() throws Exception {
        eventRepository.deleteAll();

        LocalDateTime time = LocalDateTime.of(2019, 12, 12, 13, 30);
        Event event = createEventBuilder(time).build();
        eventRepository.insert(event);

        Ticket ticket = getTicketBuilder(event).build();
        ticketRepository.insert(ticket);

        mvc.perform(get("/event"))
                .andExpect(content().string(containsString("ゴスペルワークショップ")))
                .andExpect(content().string(containsString("東京国際フォーラム")))
                .andExpect(content().string(containsString("2019/12/12 13:30")))
                .andExpect(content().string(containsString("ゴスペルワークショップのイベントです")))
                .andExpect(content().string(containsString("残りわずか")));
    }

    private Ticket.TicketBuilder getTicketBuilder(Event event) {
        return Ticket.builder()
                .eventId(event.getId())
                .ticketName("ゴスペルチケット")
                .ticketPrice(1000)
                .ticketTotal(100)
                .ticketLimit(5);
    }

    private Event.EventBuilder createEventBuilder(LocalDateTime time) {
        return Event.builder()
                .id("1")
                .name("ゴスペルワークショップ")
                .location("東京国際フォーラム")
                .owner("ゆうこ")
                .createDateTime(time)
                .updateDateTime(time)
                .summary("ゴスペルワークショップのイベントです。")
                .startDateTime(time)
                .endDateTime(time)
                .publishedDateTime(time);
    }
}

