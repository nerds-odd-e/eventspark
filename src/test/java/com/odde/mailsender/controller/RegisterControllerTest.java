package com.odde.mailsender.controller;

import com.odde.mailsender.data.RegistrationInfo;
import com.odde.mailsender.service.RegistrationInfoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
public class RegisterControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RegistrationInfoRepository registrationInfoRepository;

    @Before
    public void setup() {
        registrationInfoRepository.deleteAll();
    }

    @Test
    public void registerToEvent() throws Exception {
        mvc.perform(post("/register")
                .param("firstName", "firstName")
                .param("lastName", "lastName")
                .param("company", "companyName")
                .param("address", "aaa@example.com")
                .param("ticketType", "1day")
                .param("ticketCount", "1")
                .param("eventId", "event"))
                .andExpect(redirectedUrl("/register_complete"));

        List<RegistrationInfo> all = registrationInfoRepository.findAll();

        assertEquals(1, all.size());
        assertEquals("firstName", all.get(0).getFirstName());
        assertEquals("lastName", all.get(0).getLastName());
        assertEquals("companyName", all.get(0).getCompany());
        assertEquals("aaa@example.com", all.get(0).getAddress());
        assertEquals("1day", all.get(0).getTicketType());
        assertEquals(Integer.valueOf(1), all.get(0).getTicketCount());
        assertEquals("event", all.get(0).getEventId());
        assertNotNull(all.get(0).getId());
    }

}
