package com.odde.mailsender.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ImportCsvControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void アップロードが成功した場合コンタクトリストに遷移すること() throws Exception {
        String content = "jun.murakami@g.softbank.co.jp,Jun Murakami\nshigeru.tatsuta@g.softbank.co.jp,Shigeru Tatsuta";
        MockMultipartFile csvFile = new MockMultipartFile("file", "filename.csv", "text/plain", content.getBytes());

        mvc.perform(MockMvcRequestBuilders.multipart("/import-csv")
                .file(csvFile)
                .param("force", "false")
                .characterEncoding("UTF-8"))
                .andExpect(view().name("contact-list"))
                .andExpect(status().isOk())
                .andReturn();
    }
}
