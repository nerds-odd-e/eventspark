package com.odde.mailsender.controller;

import com.odde.mailsender.service.AddressBookService;
import com.odde.mailsender.service.FileCheckService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ImportCsvControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FileCheckService fileCheckService;
    @MockBean
    private AddressBookService addressBookService;

    @Test
    public void アップロードが成功した場合コンタクトリストに遷移すること() throws Exception {
        String content = "mail,name\njun.murakami@g.softbank.co.jp,Jun Murakami\nshigeru.tatsuta@g.softbank.co.jp,Shigeru Tatsuta";
        MockMultipartFile csvFile = new MockMultipartFile("file", "filename.csv", "text/plain", content.getBytes());

        doNothing().when(addressBookService).add(any());
        mvc.perform(MockMvcRequestBuilders.multipart("/import-csv")
                .file(csvFile)
                .param("force", "false")
                .characterEncoding("UTF-8"))
                .andExpect(view().name("contact-list"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void アップロードファイルの拡張子がcsvでない場合responsecode400が返ること() throws Exception {
        String content = "mail,name\njun.murakami@g.softbank.co.jp,Jun Murakami\nshigeru.tatsuta@g.softbank.co.jp,Shigeru Tatsuta";
        MockMultipartFile csvFile = new MockMultipartFile("file", "filename.txt", "text/plain", content.getBytes());

        mvc.perform(MockMvcRequestBuilders.multipart("/import-csv")
                .file(csvFile)
                .param("force", "false")
                .characterEncoding("UTF-8"))
                .andExpect(view().name("import-csv"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @Ignore("仕様見直し")
    public void アップロードファイルの中身がcsvでない場合responsecode400が返ること() throws Exception {
        String content = "mail,name\njun.murakami@g.softbank.co.jp\tJun Murakami\nshigeru.tatsuta@g.softbank.co.jp\tShigeru Tatsuta";
        MockMultipartFile csvFile = new MockMultipartFile("file", "filename.csv", "text/plain", content.getBytes());

        mvc.perform(MockMvcRequestBuilders.multipart("/import-csv")
                .file(csvFile)
                .param("force", "false")
                .characterEncoding("UTF-8"))
                .andExpect(view().name("import-csv"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void FileCheckServiceがNGの場合responsecode202が返ること() throws Exception {

        List<String> expected = Arrays.asList("already registered jun.murakami@g.softbank.co.jp");
        when(fileCheckService.checkUploadList(any())).thenReturn(expected);

        String content = "mail,name\njun.murakami@g.softbank.co.jp,Jun Murakami\njun.murakami@g.softbank.co.jp,Jun Murakami";
        MockMultipartFile csvFile = new MockMultipartFile("file", "filename.csv", "text/plain", content.getBytes());

        mvc.perform(MockMvcRequestBuilders.multipart("/import-csv")
                .file(csvFile)
                .param("force", "false")
                .characterEncoding("UTF-8"))
                .andExpect(view().name("import-csv"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void forceパラメータがtrueの場合Contactlistに遷移する() throws Exception {

        String content = "mail,name\njun.murakami@g.softbank.co.jp,Jun Murakami\njun.murakami@g.softbank.co.jp,Jun Murakami";
        MockMultipartFile csvFile = new MockMultipartFile("file", "filename.csv", "text/plain", content.getBytes());

        verify(fileCheckService, times(0)).checkUploadList(any());

        mvc.perform(MockMvcRequestBuilders.multipart("/import-csv")
                .file(csvFile)
                .param("force", "true")
                .characterEncoding("UTF-8"))
                .andExpect(view().name("contact-list"))
                .andExpect(status().isOk())
                .andReturn();
    }


    @Test
    public void アップロードファイルのヘッダがnameとaddressでない場合responsecode400が返ること() throws Exception {
        String content = "hoge,fuga\nyamada,test@example.com";
        MockMultipartFile csvFile = new MockMultipartFile("file", "filename.csv", "text/plain", content.getBytes());

        mvc.perform(MockMvcRequestBuilders.multipart("/import-csv")
                .file(csvFile)
                .characterEncoding("UTF-8"))
                .andExpect(view().name("import-csv"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
