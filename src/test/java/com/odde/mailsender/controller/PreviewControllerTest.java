package com.odde.mailsender.controller;

import com.odde.mailsender.data.AddressBook;
import com.odde.mailsender.data.AddressItem;
import com.odde.mailsender.service.AddressBookService;
import com.odde.mailsender.service.MailInfo;
import com.odde.mailsender.service.MailService;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.List;

import static com.odde.mailsender.service.MailBuilder.validMail;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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
                new MailInfo(null, "foo@gmx.com", "Hello name", "Hi name"),
                new PreviewNavigation(0, 0)
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
                new MailInfo("eventspark@gmx.com", "eventspark@gmx.com", "Hello Aki", "Hi Aki"),
                new PreviewNavigation(0, 0)
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
                new MailInfo(null, "hoge@fuga.com", "Hello name", "Hi name"),
                new PreviewNavigation(1, 1)
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
                new MailInfo("eventspark@gmx.com", "eventspark@gmx.com", "Hello Aki", "Hi Aki"),
                new PreviewNavigation(1, 1)
        );

        assertPreviewPage(param);
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

    private void assertPreviewPage(PreviewParameter previewParameter) throws Exception{
        MailInfo previewRequest = validMail().withSubject(previewParameter.subject).withBody(previewParameter.body).withTo(previewParameter.to).build();

        MvcResult resultActions = mvc.perform(post(previewParameter.url)
                .param("address", previewRequest.getTo())
                .param("subject", previewRequest.getSubject())
                .param("body", previewRequest.getBody()))

                .andExpect(view().name("preview"))
                .andExpect(model().attribute("mailInfo", previewParameter.expectedMailInfo))
                .andExpect(model().attribute("previewNavigation", previewParameter.exptectedPreviewNavigation))
                .andReturn();
    }

    private class PreviewParameter {
        String subject;
        String body;
        String to;

        String url;

        MailInfo expectedMailInfo;
        PreviewNavigation exptectedPreviewNavigation;

        public PreviewParameter(String subject, String body, String to, String url, MailInfo expectedMailInfo, PreviewNavigation exptectedPreviewNavigation) {
            this.subject = subject;
            this.body = body;
            this.to = to;
            this.url = url;
            this.expectedMailInfo = expectedMailInfo;
            this.exptectedPreviewNavigation = exptectedPreviewNavigation;
        }
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
