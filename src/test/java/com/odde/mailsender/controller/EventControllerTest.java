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
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private RegistrationInfoRepository registrationInfoRepository;

    @Before
    public void setup() {
        eventRepository.deleteAll();
        ticketRepository.deleteAll();
        registrationInfoRepository.deleteAll();
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
                .detail("ゴスペルワークショップ")
                .imagePath("url")
                .build();
        eventRepository.insert(event);
        Ticket ticket = Ticket.builder()
                .eventId(event.getId())
                .ticketName("ゴスペルチケット")
                .ticketPrice(1000)
                .ticketTotal(100)
                .ticketLimit(5)
                .build();
        ticketRepository.insert(ticket);
        List<Ticket> ticketList = ticketRepository.findByEventId(event.getId());

        registrationInfoRepository.save(new RegistrationInfo(
                "daiki",
                "kanai",
                "odd-e Japan",
                "sample@odd-e.com",
                ticket.getId(),
                "ticket typw",
                3,
                ticket.getEventId()
        ));

        List<RegistrationInfo> registrationInfoList = registrationInfoRepository.findByEventId(event.getId());
        List<Integer> unsoldList = new ArrayList<>();
        int sold = 0;
        for (RegistrationInfo registrationInfo : registrationInfoList) {
            sold += registrationInfo.getTicketCount();
        }
        int unsold = ticket.getTicketLimit() - sold;
        unsoldList.add(unsold);

        mvc.perform(get("/event/" + event.getName()))
                .andExpect(model().attribute("event", event))
                .andExpect(model().attribute("ticketList", ticketList))
                .andExpect(model().attribute("unsoldList", unsoldList));
    }

    @Test
    public void displayAddEventPage() throws Exception {
        MvcResult result = mvc.perform(get("/owner/event/new")).andReturn();
        assertEquals(200,result.getResponse().getStatus());
    }

    @Test
    public void newEvent() throws Exception {
        //イベントをDB追加している
        MvcResult result = performAddEvent();

        //正常終了
        assertEquals(200,result.getResponse().getStatus());

        //DBからデータを取得
        Event event=eventRepository.findByName("ゴスペルワークショップ");

        //postしたデータがDBに入っているかかくにん
        assertNotNull(event);
        assertEquals("ゴスペルワークショップ",event.getName());
        assertEquals("東京フォーラム",event.getLocation());
        assertEquals("イベントのサマリー",event.getSummary());
        assertEquals("ゆうこ",event.getOwner());
        assertEquals("アーティスト：カークフランクリン ¥n 演目：未定",event.getDetail());
        assertEquals("2019-12-20T09:00",event.getStartDateTime().toString());
        assertEquals("2019-12-21T10:00",event.getEndDateTime().toString());
        assertEquals("https://3.bp.blogspot.com/-cwPnmxNx-Ps/V6iHw4pHPgI/AAAAAAAA89I/3EUmSFZqX4oeBzDwZcIVwF0A1cyv0DsagCLcB/s800/gassyou_gospel_black.png",event.getImagePath());
    }

    private MvcResult performAddEvent() throws Exception {
        return mvc.perform(post("/owner/event")
                .param("name", "ゴスペルワークショップ")
                .param("location", "東京フォーラム")
                .param("summary", "イベントのサマリー")
                .param("owner", "ゆうこ")
                .param("detail", "アーティスト：カークフランクリン ¥n 演目：未定")
                .param("startDateTime", "2019-12-20 09:00")
                .param("endDateTime", "2019-12-21 10:00")
                .param("imagePath", "https://3.bp.blogspot.com/-cwPnmxNx-Ps/V6iHw4pHPgI/AAAAAAAA89I/3EUmSFZqX4oeBzDwZcIVwF0A1cyv0DsagCLcB/s800/gassyou_gospel_black.png")
        ).andReturn();
    }

    @Test
    public void duplicateEventName() throws Exception {
        MvcResult result = performAddEvent();

        assertEquals(200,result.getResponse().getStatus());
        Event event=eventRepository.findByName("ゴスペルワークショップ");
        assertNotNull(event);
        assertEquals("ゴスペルワークショップ",event.getName());

        //重複するイベントを追加して検証
        result = performAddEvent();

        assertEquals(200, result.getResponse().getStatus());
        assertEquals("event-new", result.getModelAndView().getViewName());

    }

}
