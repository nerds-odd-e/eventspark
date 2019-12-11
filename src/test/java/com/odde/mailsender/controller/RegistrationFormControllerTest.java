package com.odde.mailsender.controller;

import com.odde.mailsender.data.Event;
import com.odde.mailsender.data.Ticket;
import com.odde.mailsender.form.RegisterForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
public class RegistrationFormControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void showRegistrationForm() throws Exception {
        List<Ticket> ticketList = new ArrayList<>();
        ticketList.add(Ticket.builder().ticketName("1day").ticketPrice(1000L).ticketTotal(100L).ticketLimit(10).eventId("event").build());
        ticketList.add(Ticket.builder().ticketName("2days").ticketPrice(1800L).ticketTotal(100L).ticketLimit(10).eventId("event").build());

        mvc.perform(get("/register_form/TestEvent"))
                .andExpect(view().name("register_form"))
                .andExpect(model().attribute("event", allOf(
                        hasProperty("name", is("TestEvent")),
                        is(not(nullValue()))
                )))
                .andExpect(model().attribute("event", Event.builder().id("1").name("TestEvent").build()))
                .andExpect(model().attribute("ticketList", ticketList));
    }

}
