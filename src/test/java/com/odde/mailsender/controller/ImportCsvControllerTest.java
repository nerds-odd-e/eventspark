package com.odde.mailsender.controller;

import com.odde.mailsender.service.AddressBookService;
import com.odde.mailsender.service.FileCheckService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ImportCsvControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MockHttpSession mockHttpSession;

    @MockBean
    private FileCheckService fileCheckService;
    @MockBean
    private AddressBookService addressBookService;

    String validContent = "mail,name\nty@example.com,Taro Yamada\nhnk@example.com,Hanako Suzuki";
    String invalid3Columns = "mail,name\nhnk@example.com,Hanako Suzuki,test\nty@example.com\tTaro Yamada";
    String invalidHeaderNamesContent = "hoge,fuga\nyamada,test@example.com";

    @Test
    public void アップロードが成功した場合コンタクトリストに遷移すること() throws Exception {
        doNothing().when(addressBookService).add(any());

        performPost("/import-csv", validContactCsvFile())
                .andExpectSuccess("contact-list")
                .andReturn();
    }

    @Test
    public void FileCheckServiceがNGの場合HTTP_OKが返ること() throws Exception {
        when(fileCheckService.checkUploadList(any()))
                .thenReturn(Collections.singletonList("already registered hnk@example.com"));

        performPost("/import-csv", validContactCsvFile())
                .andExpectSuccess("import-csv")
                .andReturn();
    }

    @Test
    public void アップロードファイルの拡張子がcsvでない場合responsecode400が返ること() throws Exception {
        performPost("/import-csv", aTxtFile())
                .andExpectError("Please specify csv file.", "import-csv")
                .andReturn();
    }

    @Test
    public void アップロードファイルの中身が想定したcsvでない場合responsecode400が返ること() throws Exception {
        performPost("/import-csv", csvFileWithContent(invalid3Columns))
                .andExpectError("CSV must have 2 fields(mail,name).", "import-csv")
                .andReturn();
    }

    @Test
    public void アップロードファイルのヘッダがmailとnameでない場合responsecode400が返ること() throws Exception {
        performPost("/import-csv", csvFileWithContent(invalidHeaderNamesContent))
                .andExpectError("CSV file header requires mail,name.", "import-csv")
                .andReturn();
    }

    @Test
    public void アップロードファイルがバイナリファイルの場合responsecode400が返ること() throws Exception {
        performPost("/import-csv", aBinaryFile())
                .andExpectError("Uploaded file is binary data.", "import-csv")
                .andReturn();
    }


    private ResultActionsHelper performPost(String url, MockMultipartFile csvFile) throws Exception {
        return new ResultActionsHelper(mvc.perform(MockMvcRequestBuilders.multipart(url)
                .file(csvFile)
                .characterEncoding("UTF-8")));
    }


    private MockMultipartFile csvFileWithContent(String content) {
        return new MockMultipartFile("file", "filename.csv", "text/plain", content.getBytes());
    }

    private MockMultipartFile validContactCsvFile() {
        return new MockMultipartFile("file", "filename.csv", "text/plain", validContent.getBytes());
    }

    private MockMultipartFile aTxtFile() {
        return new MockMultipartFile("file", "filename.txt", "text/plain", validContent.getBytes());
    }

    private MockMultipartFile aBinaryFile() {
        byte[] content = new byte[]{0, 0, 0, 0, 0};
        return new MockMultipartFile("file", "filename.csv", "text/plain", content);
    }

    private class ResultActionsHelper {
        private ResultActions actions;

        public ResultActionsHelper(ResultActions actions) {
            this.actions = actions;
        }

        public ResultActions andExpectError(String errorMessage, String expectedViewName) throws Exception {
            return actions.andExpect(model().attribute("errors", Arrays.asList(errorMessage)))
                    .andExpect(view().name(expectedViewName))
                    .andExpect(status().isBadRequest());
        }


        public ResultActions andExpectSuccess(String pageName) throws Exception {
            return actions.andExpect(view().name(pageName))
                    .andExpect(status().isOk());
        }
    }
}
