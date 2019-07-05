package com.odde.mailsender.controller;

import com.odde.mailsender.data.AddressBook;
import com.odde.mailsender.data.AddressItem;
import com.odde.mailsender.service.AddressBookService;
import com.odde.mailsender.service.MailInfo;
import com.odde.mailsender.service.PreviewNavigation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PreviewControllerTest {

    @MockBean
    private AddressBookService addressBookService;

    @Autowired
    private MockMvc mvc;

    @Before
    public void setUp() {
        File file = new File(AddressBook.FILE_PATH);
        file.delete();

        when(addressBookService.findByAddress("foo@gmx.com")).thenReturn(
                new AddressItem("foo@gmx.com", "Aki")
        );

        when(addressBookService.findByAddress("hoge@fuga.com")).thenReturn(
                new AddressItem("hoge@fuga.com", "Daiki")
        );
    }

    @Test
    public void showPreviewPageWithoutParam() throws Exception {
        InputMailContents inputMailContents = new InputMailContents(
                "subject text",
                "body text",
                "foo@gmx.com"
        );

        MailInfo expectedMailInfo = new MailInfo(null, "foo@gmx.com", "subject text", "body text");
        PreviewNavigation expectedPreviewNavigation = new PreviewNavigation(0, 0);

        postPreviewEndpoint(inputMailContents, "/preview/0")
                .andExpectResult(expectedMailInfo, expectedPreviewNavigation);
    }

    @Test
    public void showPreviewPageWithParam() throws Exception {
        addressBookService.add(new AddressItem("foo@gmx.com", "Aki"));

        InputMailContents inputMailContents = new InputMailContents(
                "subject $name",
                "body $name",
                "foo@gmx.com"
        );

        MailInfo expectedMailInfo = new MailInfo("eventspark@gmx.com", "foo@gmx.com", "subject Aki", "body Aki");
        PreviewNavigation expectedPreviewNavigation = new PreviewNavigation(0, 0);

        postPreviewEndpoint(inputMailContents, "/preview/0")
                .andExpectResult(expectedMailInfo, expectedPreviewNavigation);
    }

    @Test
    public void showPreviewNextPageWithoutParam() throws Exception {
        InputMailContents inputMailContents = new InputMailContents(
                "subject text",
                "body text",
                "foo@gmx.com;hoge@fuga.com"
        );

        MailInfo expectedMailInfo = new MailInfo(null, "hoge@fuga.com", "subject text", "body text");
        PreviewNavigation expectedPreviewNavigation = new PreviewNavigation(1, 1);

        postPreviewEndpoint(inputMailContents, "/preview/1")
                .andExpectResult(expectedMailInfo, expectedPreviewNavigation);
    }

    @Test
    public void showPreviewPage1WithParam() throws Exception {
        addressBookService.add(new AddressItem("hoge@fuga.com", "Daiki"));

        InputMailContents inputMailContents = new InputMailContents(
                "subject $name",
                "body $name",
                "foo@gmx.com;hoge@fuga.com"
        );

        MailInfo expectedMailInfo = new MailInfo("eventspark@gmx.com", "hoge@fuga.com", "subject Daiki", "body Daiki");
        PreviewNavigation expectedPreviewNavigation = new PreviewNavigation(1, 1);

        postPreviewEndpoint(inputMailContents, "/preview/1")
                .andExpectResult(expectedMailInfo, expectedPreviewNavigation);

    }

    private ResultActionsHelper postPreviewEndpoint(InputMailContents inputMailContents, String url) throws Exception {
        return new ResultActionsHelper(
                mvc.perform(post(url)
                    .param("address", inputMailContents.to)
                    .param("subject", inputMailContents.subject)
                    .param("body", inputMailContents.body)));
    }

    @Test
    public void manyAddressWithInvalidAddressAndNoSubject() throws Exception {
        MvcResult resultActions = mvc.perform(post("/preview/0")
                .param("address", "abcdefghi123@xxx.com ; xxx.com; stanly@xxx.com")
                .param("subject", "")
                .param("body", ""))
                .andExpect(view().name("home"))
                .andReturn();

        assertErrorMessage(resultActions, "address", "Address format is wrong");
        assertErrorMessage(resultActions, "subject", "{0} may not be empty");
    }

    @Test
    public void showErrorIfEmptyForms() throws Exception {
        MvcResult resultActions = mvc.perform(post("/preview/0")
                .param("address", "")
                .param("subject", "")
                .param("body", ""))
                .andExpect(view().name("home"))
                .andReturn();

        assertErrorMessage(resultActions, "address", "{0} may not be empty");
        assertErrorMessage(resultActions, "subject", "{0} may not be empty");
        assertErrorMessage(resultActions, "body", "{0} may not be empty");
    }

    private class ResultActionsHelper {


        private ResultActions resultActions;

        private ResultActionsHelper(ResultActions resultActions) {
            this.resultActions = resultActions;
        }

        private void andExpectResult(MailInfo mailInfo, PreviewNavigation previewNavigation) throws Exception {
            resultActions.andExpect(view().name("preview"))
                    .andExpect(model().attribute("mailInfo", mailInfo))
                    .andExpect(model().attribute("previewNavigation", previewNavigation));
        }


    }

    private void assertErrorMessage(MvcResult mvcResult, String errorMessage, String errorTemplateMessage) {
        ModelAndView mav = mvcResult.getModelAndView();
        List<ObjectError> objectErrors = ((BindingResult) mav.getModel().get(
                "org.springframework.validation.BindingResult.form")).getAllErrors();


        if (objectErrors.stream().filter(i -> i instanceof FieldError).count() > 0) {
            assertTrue(objectErrors.stream().map(i -> ((FieldError) i))
                    .anyMatch(i -> i.getField().equals(errorMessage) && i.getDefaultMessage().equals(errorTemplateMessage)));
        } else {
            assertTrue(objectErrors.stream()
                    .anyMatch(i -> i.getDefaultMessage().equals(errorTemplateMessage)));
        }


    }

    private class InputMailContents {
        private final String subject;
        private final String body;
        private final String to;

        private InputMailContents(String subject, String body, String to) {
            this.subject = subject;
            this.body = body;
            this.to = to;
        }
    }
}
