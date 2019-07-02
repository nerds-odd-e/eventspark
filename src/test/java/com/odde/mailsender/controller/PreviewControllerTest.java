package com.odde.mailsender.controller;

import com.odde.mailsender.service.MailInfo;
import com.odde.mailsender.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PreviewControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void showPreviewPage() throws Exception {
        ResultActions resultActions = mvc.perform(post("/preview"));
//                .param("from", mailInfo.getFrom())
//                .param("address", mailInfo.getTo())
//                .param("subject", mailInfo.getSubject())
//                .param("body", mailInfo.getBody()));

        resultActions.andExpect(view().name("preview"));
    }
}
