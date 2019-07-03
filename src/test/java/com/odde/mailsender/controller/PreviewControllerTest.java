package com.odde.mailsender.controller;

import com.odde.mailsender.data.AddressBook;
import com.odde.mailsender.data.AddressItem;
import com.odde.mailsender.service.AddressBookService;
import com.odde.mailsender.service.MailInfo;
import com.odde.mailsender.service.MailService;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.List;

import static com.odde.mailsender.service.MailBuilder.validMail;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PreviewControllerTest {

    @MockBean
    private MailService mailService;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private MockMvc mvc;

    @Before
    public void setUp() {
        File file = new File(AddressBook.FILE_PATH);
        boolean isDelete = file.delete();
    }

    @Test
    public void showPreviewPageWithoutParam() throws Exception {
        PreviewParameter param = new PreviewParameter(
                "Hello name",
                "Hi name",
                "foo@gmx.com",
                "/preview/0",
                "foo@gmx.com",
                "Hello name",
                "Hi name",
                -1,
                1,
                false,
                false
        );

        assertPreviewPage(param);
    }

    @Test
    public void showPreviewPageWithParam() throws Exception {

        addressBookService.add(new AddressItem("eventspark@gmx.com", "Aki"));

        PreviewParameter param = new PreviewParameter(
                "Hello $name",
                "Hi $name",
                "eventspark@gmx.com",
                "/preview/0",
                "eventspark@gmx.com",
                "Hello Aki",
                "Hi Aki",
                -1,
                1,
                false,
                false
        );

        assertPreviewPage(param);
    }

    @Test
    public void showPreviewPage1WithoutParam() throws Exception {

        PreviewParameter param = new PreviewParameter(
                "Hello name",
                "Hi name",
                "foo@gmx.com;hoge@fuga.com",
                "/preview/1",
                "hoge@fuga.com",
                "Hello name",
                "Hi name",
                0,
                2,
                true,
                false
        );

        assertPreviewPage(param);
    }

    @Test
    public void showPreviewPage1WithParam() throws Exception {
        addressBookService.add(new AddressItem("eventspark@gmx.com", "Aki"));

        PreviewParameter param = new PreviewParameter(
                "Hello $name",
                "Hi $name",
                "hoge@fuga.com;eventspark@gmx.com",
                "/preview/1",
                "eventspark@gmx.com",
                "Hello Aki",
                "Hi Aki",
                0,
                2,
                true,
                false
        );

        assertPreviewPage(param);
    }

    private class PreviewParameter {
        String subject;
        String body;
        String to;

        String url;

        String expectedAddress;
        String expectedSubject;
        String expectedBody;
        int expectedPrevIndex;
        int expectedNextIndex;
        boolean expectedShowPrev;
        boolean expectedShowNext;

        public PreviewParameter(String subject, String body, String to, String url, String expectedAddress, String expectedSubject, String expectedBody, int expectedPrevIndex, int expectedNextIndex, boolean expectedShowPrev, boolean expectedShowNext) {
            this.subject = subject;
            this.body = body;
            this.to = to;
            this.url = url;
            this.expectedAddress = expectedAddress;
            this.expectedSubject = expectedSubject;
            this.expectedBody = expectedBody;
            this.expectedPrevIndex = expectedPrevIndex;
            this.expectedNextIndex = expectedNextIndex;
            this.expectedShowPrev = expectedShowPrev;
            this.expectedShowNext = expectedShowNext;
        }
    }

    private void assertPreviewPage(PreviewParameter previewParameter) throws Exception{
        MailInfo previewRequest = validMail().withSubject(previewParameter.subject).withBody(previewParameter.body).withTo(previewParameter.to).build();

        MvcResult resultActions = mvc.perform(post(previewParameter.url)
                .param("address", previewRequest.getTo())
                .param("subject", previewRequest.getSubject())
                .param("body", previewRequest.getBody()))

                .andExpect(view().name("preview"))
                .andExpect(model().attribute("address", previewParameter.expectedAddress))
                .andExpect(model().attribute("subject", previewParameter.expectedSubject))
                .andExpect(model().attribute("body", previewParameter.expectedBody))

                .andExpect(model().attribute("prevIndex", previewParameter.expectedPrevIndex))
                .andExpect(model().attribute("nextIndex", previewParameter.expectedNextIndex))
                .andExpect(model().attribute("showPrev", previewParameter.expectedShowPrev))
                .andExpect(model().attribute("showNext", previewParameter.expectedShowNext))
                .andReturn();
    }

    private void assertErrorMessage(MvcResult mvcResult, String errorMessage, String errorTemplateMessage) {
        ModelAndView mav = mvcResult.getModelAndView();
        List<ObjectError> objectErrors = ((BindingResult) mav.getModel().get(
                "org.springframework.validation.BindingResult.form")).getAllErrors();


        if (objectErrors.stream().filter(i -> i instanceof FieldError).count() > 0) {
            assertTrue(objectErrors.stream().map(i -> ((FieldError) i))
                    .anyMatch(i -> i.getField() == null ?
                            i.getDefaultMessage().equals(errorTemplateMessage) :
                            i.getField().equals(errorMessage) && i.getDefaultMessage().equals(errorTemplateMessage)));
        } else {
            assertTrue(objectErrors.stream()
                    .anyMatch(i -> i.getDefaultMessage().equals(errorTemplateMessage)));
        }


    }
}
