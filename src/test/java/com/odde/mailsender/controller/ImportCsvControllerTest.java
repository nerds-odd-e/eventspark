package com.odde.mailsender.controller;

import com.odde.mailsender.data.AddressItem;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        MockMultipartFile csvFile = validContactCsvFile();

        List<AddressItem> uploadList = new ArrayList<>();
        uploadList.add(new AddressItem("jun.murakami@g.softbank.co.jp", "Jun Murakami"));
        uploadList.add(new AddressItem("shigeru.tatsuta@g.softbank.co.jp", "Shigeru Tatsuta"));

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
        MockMultipartFile csvFile = contactCsvFileWithFilename("filename.txt");

        mvc.perform(MockMvcRequestBuilders.multipart("/import-csv")
                .file(csvFile)
                .param("force", "false")
                .characterEncoding("UTF-8"))
                .andExpect(model().attribute("errors", Arrays.asList("Please specify csv file.")))
                .andExpect(view().name("import-csv"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }



    @Test
    //@Ignore("仕様見直し")
    public void アップロードファイルの中身が想定したcsvでない場合responsecode400が返ること() throws Exception {
        MockMultipartFile csvFile = contactsCsvFileWithContent(invalid3Columns);

        mvc.perform(MockMvcRequestBuilders.multipart("/import-csv")
                .file(csvFile)
                .param("force", "false")
                .characterEncoding("UTF-8"))
                .andExpect(model().attribute("errors", Arrays.asList("CSV must have 2 fields(mail,name).")))
                .andExpect(view().name("import-csv"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }


    @Test
    public void アップロードファイルのヘッダがmailとnameでない場合responsecode400が返ること() throws Exception {
        MockMultipartFile csvFile = contactsCsvFileWithContent(invalidHeaderNamesContent);

        mvc.perform(MockMvcRequestBuilders.multipart("/import-csv")
                .file(csvFile)
                .characterEncoding("UTF-8"))
                .andExpect(model().attribute("errors", Arrays.asList("CSV file header requires mail,name.")))
                .andExpect(view().name("import-csv"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void アップロードファイルがバイナリファイルの場合responsecode400が返ること() throws Exception {
        MockMultipartFile csvFile = invalidCsvBinaryFile();

        mvc.perform(MockMvcRequestBuilders.multipart("/import-csv")
                .file(csvFile)
                .characterEncoding("UTF-8"))
                .andExpect(model().attribute("errors", Arrays.asList("Uploaded file is binary data.")))
                .andExpect(view().name("import-csv"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }


    @Test
    public void FileCheckServiceがNGの場合HTTP_OKが返ること() throws Exception {

        List<String> expected = Arrays.asList("already registered hnk@example.com");
        when(fileCheckService.checkUploadList(any())).thenReturn(expected);


        MockMultipartFile csvFile = validContactCsvFile();

        mvc.perform(MockMvcRequestBuilders.multipart("/import-csv")
                .file(csvFile)
                .param("force", "false")
                .characterEncoding("UTF-8"))
                .andExpect(view().name("import-csv"))
                .andExpect(status().isOk())
                .andReturn();
    }

    private MockMultipartFile contactsCsvFileWithContent(String content) {
        return new MockMultipartFile("file", "filename.csv", "text/plain", content.getBytes());
    }

    private MockMultipartFile validContactCsvFile() {
        return new MockMultipartFile("file", "filename.csv", "text/plain", validContent.getBytes());
    }

    private MockMultipartFile contactCsvFileWithFilename(String filename) {
        return new MockMultipartFile("file", filename, "text/plain", validContent.getBytes());
    }

    private MockMultipartFile invalidCsvBinaryFile() {
        byte[] content = new byte[]{0,0,0,0,0};
        return new MockMultipartFile("file", "filename.csv", "text/plain", content);
    }

//    @Test
//    public void forceパラメータがtrueの場合Contactlistに遷移する() throws Exception {
//
//        String content = "mail,name\nty@example.com,Taro Yamada\nty@example.com,Taro Yamada";
//        MockMultipartFile csvFile = new MockMultipartFile("file", "filename.csv", "text/plain", content.getBytes());
//
//        List<AddressItem> uploadList = new ArrayList<>();
//        uploadList.add(new AddressItem("jun.murakami@g.softbank.co.jp", "Jun Murakami"));
//        uploadList.add(new AddressItem("shigeru.tatsuta@g.softbank.co.jp", "Shigeru Tatsuta"));
//        when(mockHttpSession.getAttribute("addressItems")).thenReturn(uploadList);
//
//        verify(fileCheckService, times(0)).checkUploadList(any());
//
//        mvc.perform(MockMvcRequestBuilders.multipart("/import-csv")
//                .file(csvFile)
//                .param("force", "true")
//                .characterEncoding("UTF-8"))
//                .andExpect(view().name("contact-list"))
//                .andExpect(status().isOk())
//                .andReturn();
//    }



}
