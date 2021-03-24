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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    private Event aEvent = validEvent();

    @Test
    public void displayEventDetail() throws Exception {
        eventRepository.insert(aEvent);
        Ticket ticket = validTicket();
        ticketRepository.insert(ticket);
        List<Ticket> ticketList = ticketRepository.findByEventId(aEvent.getId());

        mvc.perform(get("/event/" + aEvent.getName()))
                .andExpect(model().attribute("event", aEvent))
                .andExpect(model().attribute("ticketList", ticketList));
        registrationInfoRepository.save(RegistrationInfo.builder()
                .firstName("daiki")
                .lastName("kanai")
                .company("odd-e Japan")
                .address("sample@odd-e.com")
                .ticketId(ticket.getId())
                .ticketCount(3)
                .eventId(ticket.getEventId()).build()
        );


        List<RegistrationInfo> registrationInfoList = registrationInfoRepository.findByEventId(aEvent.getId());
        List<Long> unsoldList = new ArrayList<>();
        long sold = 0;
        for (RegistrationInfo registrationInfo : registrationInfoList) {
            sold += registrationInfo.getTicketCount();
        }
        long unsold = ticket.getTicketTotal() - sold;
        unsoldList.add(unsold);

        mvc.perform(get("/event/" + aEvent.getName()))
                .andExpect(model().attribute("event", aEvent))
                .andExpect(model().attribute("ticketList", ticketList))
                .andExpect(model().attribute("unsoldList", unsoldList));
    }


    @Test
    public void notExistDisplayEventDetail() throws Exception {
        MvcResult result = mvc.perform(get("/event/" + "no exist event")).andReturn();
        assertEquals(404, result.getResponse().getStatus());
    }

    @Test
    public void displayAddEventPage() throws Exception {
        MvcResult result = mvc.perform(get("/owner/event/new")).andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    public void newEvent() throws Exception {
        //イベントをDB追加している
        MvcResult result = performAddEvent();

        //正常終了
        assertEquals(200, result.getResponse().getStatus());

        //DBからデータを取得
        Event actualEvent = eventRepository.findByName(aEvent.getName());

        //postしたデータがDBに入っているかかくにん
        assertNotNull(actualEvent);
        assertEquals(aEvent.getName(), actualEvent.getName());
        assertEquals(aEvent.getLocation(), actualEvent.getLocation());
        assertEquals(aEvent.getSummary(), actualEvent.getSummary());
        assertEquals(aEvent.getOwner(), actualEvent.getOwner());
        assertEquals(aEvent.getDetail(), actualEvent.getDetail());
        assertEquals(aEvent.getStartDateTime(), actualEvent.getStartDateTime());
        assertEquals(aEvent.getEndDateTime(), actualEvent.getEndDateTime());
        assertEquals(aEvent.getImagePath(), actualEvent.getImagePath());
    }


    @Test
    public void duplicateEventName() throws Exception {
        eventRepository.save(aEvent);

        //重複するイベントを追加して検証
        MvcResult result = performAddEvent();

        assertEquals(200, result.getResponse().getStatus());
        assertEquals("event-new", result.getModelAndView().getViewName());
        assertEquals("Failed!: Same name event already exist.", result.getModelAndView().getModel().get("errorMessage"));

    }

    @Test
    public void invalidParameterEvent() throws Exception {
        MvcResult result = performAddEventWithoutStartDataTime();

        assertEquals(200, result.getResponse().getStatus());
        assertEquals("event-new", result.getModelAndView().getViewName());
        assertEquals("There is an error in the input contents.", result.getModelAndView().getModel().get("errorMessage"));
    }

    @Test
    public void updateEvent() throws Exception {
        eventRepository.save(aEvent);
        Event editEvent = eventRepository.findByName(aEvent.getName());
        editEvent.setLocation("幕張メッセ");
        MvcResult result = performEditEvent(editEvent);
        assertThat(result.getResponse().getStatus(), is(equalTo(200)));

        Event editedEvent = eventRepository.findByName(aEvent.getName());
        assertThat(editedEvent.getName(), is(equalTo(aEvent.getName())));
        assertThat(editedEvent.getLocation(), is(equalTo(editEvent.getLocation())));
    }

    private Event validEvent() {
        LocalDateTime createdDateTime = LocalDateTime.of(2019,11,1,9,0);
        return Event.builder()
                .name("ゴスペルワークショップ")
                .location("東京国際フォーラム")
                .owner("ゆうこ")
                .createDateTime(createdDateTime)
                .updateDateTime(createdDateTime)
                .summary("ゴスペルワークショップのイベントです。")
                .startDateTime(LocalDateTime.of(2019,12,20,9,0))
                .endDateTime(LocalDateTime.of(2019,12,21,17,0))
                .publishedDateTime(LocalDateTime.of(2019,12,1,9,0))
                .detail("ゴスペルワークショップ")
                .imagePath("https://3.bp.blogspot.com/-cwPnmxNx-Ps/V6iHw4pHPgI/AAAAAAAA89I/3EUmSFZqX4oeBzDwZcIVwF0A1cyv0DsagCLcB/s800/gassyou_gospel_black.png")
                .build();
    }

    private Ticket validTicket() {
        return Ticket.builder()
                .eventId(aEvent.getId())
                .ticketName("ゴスペルチケット")
                .ticketPrice(1000L)
                .ticketTotal(100L)
                .ticketLimit(5)
                .build();
    }

    private MvcResult performAddEvent() throws Exception {
        return mvc.perform(post("/owner/event")
                .param("name", aEvent.getName())
                .param("location", aEvent.getLocation())
                .param("summary", aEvent.getSummary())
                .param("owner", aEvent.getOwner())
                .param("detail", aEvent.getDetail())
                .param("startDateTime", "2019-12-20 09:00")
                .param("endDateTime", "2019-12-21 17:00")
                .param("imagePath", aEvent.getImagePath())
        ).andReturn();
    }

    private MvcResult performEditEvent(Event editEvent) throws Exception {
        return mvc.perform(put("/owner/event/"+ editEvent.getName())
                .param("name", editEvent.getName())
                .param("location", editEvent.getLocation())
                .param("summary", editEvent.getSummary())
                .param("owner", editEvent.getOwner())
                .param("detail", editEvent.getDetail())
                .param("startDateTime", editEvent.getStartDateTime().format(DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm")))
                .param("endDateTime", editEvent.getStartDateTime().format(DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm")))
                .param("imagePath", editEvent.getImagePath())
        ).andReturn();
    }

    private MvcResult performAddEventWithoutStartDataTime() throws Exception {
        return mvc.perform(post("/owner/event")
                        .param("name", aEvent.getName())
                        .param("location", aEvent.getLocation())
                        .param("summary", aEvent.getSummary())
                        .param("owner", aEvent.getOwner())
                        .param("detail", aEvent.getDetail())
                        .param("endDateTime", "2019-12-21 17:00")
                        .param("imagePath", aEvent.getImagePath())
        ).andReturn();
    }
}
