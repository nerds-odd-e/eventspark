package com.odde.mailsender.controller;

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

import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PreviewControllerTest {

    private static final String FOO_EMAIL = "foo@gmx.com";
    private static final String FOO_NAME = "Foo";
    private static final String HOGE_EMAIL = "hoge@fuga.com";
    private static final String HOGE_NAME = "Daiki";
    private static final String SENDER_EMAIL = "eventspark@gmx.com";

    private InputMailContents noAttributesMail;
    private InputMailContents aMail;

    @MockBean
    private AddressBookService addressBookService;

    @Autowired
    private MockMvc mvc;


    @Before
    public void setUp() {
        when(addressBookService.findByAddress(FOO_EMAIL)).thenReturn(
                new AddressItem(FOO_EMAIL, FOO_NAME)
        );

        when(addressBookService.findByAddress(HOGE_EMAIL)).thenReturn(
                new AddressItem(HOGE_EMAIL, HOGE_NAME)
        );

        noAttributesMail = new InputMailContents(
                "subject text",
                "body text",
                FOO_EMAIL
        );

        aMail = new InputMailContents();
    }

    @Test
    public void showPreviewPageWithoutParam() throws Exception {
        performPost(noAttributesMail, "/preview/0")
                .andExpectPreviewView()
                .andExpectMailInfo(new MailInfo(null, noAttributesMail.to, noAttributesMail.subject, noAttributesMail.body))
                .andExpectNavigation(new PreviewNavigation(0, 0));
    }

    @Test
    public void showPreviewPageWithParam() throws Exception {
        aMail.subject = "subject $name";
        aMail.body = "body $name";

        performPost(aMail, "/preview/0")
                .andExpectPreviewView()
                .andExpectMailInfo(new MailInfo(SENDER_EMAIL, aMail.to, "subject " + FOO_NAME, "body " + FOO_NAME))
                .andExpectNavigation(new PreviewNavigation(0, 0));
    }

    @Test
    public void showPreviewNextPageWithoutParam() throws Exception {
        noAttributesMail.to = FOO_EMAIL + ";" + HOGE_EMAIL;

        performPost(noAttributesMail, "/preview/1")
                .andExpectPreviewView()
                .andExpectMailInfo(new MailInfo(null, HOGE_EMAIL, noAttributesMail.subject, noAttributesMail.body))
                .andExpectNavigation(new PreviewNavigation(1, 1));
    }

    @Test
    public void showPreviewNextPageWithParam() throws Exception {
        addressBookService.add(new AddressItem(HOGE_EMAIL, HOGE_NAME));

        aMail.subject = "subject $name";
        aMail.body = "body $name";
        aMail.to = FOO_EMAIL + ";" + HOGE_EMAIL;

        performPost(aMail, "/preview/1")
                .andExpectPreviewView()
                .andExpectMailInfo(new MailInfo(SENDER_EMAIL, HOGE_EMAIL, "subject " + HOGE_NAME, "body " + HOGE_NAME))
                .andExpectNavigation(new PreviewNavigation(1, 1));
    }

    @Test
    public void manyAddressWithInvalidAddressAndNoSubject() throws Exception {
        InputMailContents emptySubjectsAndBody = new InputMailContents(
                "",
                "",
                "abcdefghi123@xxx.com ; xxx.com; stanly@xxx.com"
        );

        MvcResult mvcResult = postPreviewEndpoint(emptySubjectsAndBody, "/preview/0")
                                .andExpect(view().name("home"))
                                .andReturn();

        assertErrorMessage(mvcResult, "address", "Address format is wrong");
        assertErrorMessage(mvcResult, "subject", "{0} may not be empty");
    }

    @Test
    public void showErrorIfEmptyForms() throws Exception {
        InputMailContents emptyContents = new InputMailContents(
                "",
                "",
                ""
        );

        MvcResult mvcResult = postPreviewEndpoint(emptyContents, "/preview/0")
                                .andExpect(view().name("home"))
                                .andReturn();

        assertErrorMessage(mvcResult, "address", "{0} may not be empty");
        assertErrorMessage(mvcResult, "subject", "{0} may not be empty");
        assertErrorMessage(mvcResult, "body", "{0} may not be empty");
    }

    private void assertErrorMessage(MvcResult mvcResult, String errorMessage, String errorTemplateMessage) {
        ModelAndView mav = mvcResult.getModelAndView();
        assert mav != null;
        List<ObjectError> objectErrors = ((BindingResult) mav.getModel().get(
                "org.springframework.validation.BindingResult.form")).getAllErrors();

        Stream<FieldError> fieldErrorStream = objectErrors.stream().map(i -> ((FieldError) i));

        assertTrue(
                fieldErrorStream.anyMatch(
                        i -> i.getField().equals(errorMessage) && errorTemplateMessage.equals(i.getDefaultMessage())
                )
        );
    }

    private ResultActions postPreviewEndpoint(InputMailContents inputMailContents, String url) throws Exception {
        return mvc.perform(post(url)
                .param("address", inputMailContents.to)
                .param("subject", inputMailContents.subject)
                .param("body", inputMailContents.body));
    }

    private ResultActionsHelper performPost(InputMailContents inputMailContents, String url) throws Exception {
        return new ResultActionsHelper(mvc.perform(post(url)
                .param("address", inputMailContents.to)
                .param("subject", inputMailContents.subject)
                .param("body", inputMailContents.body)));
    }


    private class InputMailContents {
        private String subject;
        private String body;
        private String to;

        private InputMailContents(String subject, String body, String to) {
            this.subject = subject;
            this.body = body;
            this.to = to;
        }

        public InputMailContents() {
            this.to = FOO_EMAIL;
        }
    }

    private class ResultActionsHelper {
        private ResultActions actions;

        public ResultActionsHelper(ResultActions actions) {
            this.actions = actions;
        }

        public ResultActionsHelper andExpectPreviewView() throws Exception {
            actions.andExpect(view().name("preview"));
            return this;
        }

        public ResultActionsHelper andExpectMailInfo(MailInfo mailInfo) throws Exception {
            actions.andExpect(model().attribute("mailInfo", mailInfo));
            return this;
        }

        public ResultActionsHelper andExpectNavigation(PreviewNavigation previewNavigation) throws Exception {
            actions.andExpect(model().attribute("previewNavigation", previewNavigation));
            return this;
        }
    }
}
