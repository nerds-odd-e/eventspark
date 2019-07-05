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

    public static final String fooEmail = "foo@gmx.com";
    public static final String fooName = "Foo";
    public static final String hogeEmail = "hoge@fuga.com";
    public static final String hogeName = "Daiki";
    public static final String senderEmail = "eventspark@gmx.com";

    @MockBean
    private AddressBookService addressBookService;

    @Autowired
    private MockMvc mvc;

    @Before
    public void setUp() {
        File file = new File(AddressBook.FILE_PATH);
        file.delete();

        when(addressBookService.findByAddress(fooEmail)).thenReturn(
                new AddressItem(fooEmail, fooName)
        );

        when(addressBookService.findByAddress(hogeEmail)).thenReturn(
                new AddressItem(hogeEmail, hogeName)
        );
    }

    @Test
    public void showPreviewPageWithoutParam() throws Exception {
        InputMailContents inputMailContents = new InputMailContents(
                "subject text",
                "body text",
                fooEmail
        );

        MailInfo expectedMailInfo = new MailInfo(null, fooEmail, "subject text", "body text");
        PreviewNavigation expectedPreviewNavigation = new PreviewNavigation(0, 0);

        postAndExpect("/preview/0", inputMailContents, expectedMailInfo, expectedPreviewNavigation);
    }

    @Test
    public void showPreviewPageWithParam() throws Exception {
        addressBookService.add(new AddressItem(fooEmail, fooName));

        InputMailContents inputMailContents = new InputMailContents(
                "subject $name",
                "body $name",
                fooEmail
        );

        MailInfo expectedMailInfo = new MailInfo(senderEmail, fooEmail, "subject " + fooName, "body " + fooName);
        PreviewNavigation expectedPreviewNavigation = new PreviewNavigation(0, 0);

        postAndExpect("/preview/0", inputMailContents, expectedMailInfo, expectedPreviewNavigation);
    }

    @Test
    public void showPreviewNextPageWithoutParam() throws Exception {
        InputMailContents inputMailContents = new InputMailContents(
                "subject text",
                "body text",
                fooEmail + ";" + hogeEmail
        );

        MailInfo expectedMailInfo = new MailInfo(null, hogeEmail, "subject text", "body text");
        PreviewNavigation expectedPreviewNavigation = new PreviewNavigation(1, 1);

        postAndExpect("/preview/1", inputMailContents, expectedMailInfo, expectedPreviewNavigation);
    }

    @Test
    public void showPreviewNextPageWithParam() throws Exception {
        addressBookService.add(new AddressItem(hogeEmail, hogeName));

        InputMailContents inputMailContents = new InputMailContents(
                "subject $name",
                "body $name",
                fooEmail + ";" + hogeEmail
        );

        MailInfo expectedMailInfo = new MailInfo(senderEmail, hogeEmail, "subject " + hogeName, "body " + hogeName);
        PreviewNavigation expectedPreviewNavigation = new PreviewNavigation(1, 1);

        postAndExpect("/preview/1", inputMailContents, expectedMailInfo, expectedPreviewNavigation);

    }

    private void postAndExpect(
            String url,
            InputMailContents inputMailContents,
            MailInfo expectedMailInfo,
            PreviewNavigation expectedPreviewNavigation
    ) throws Exception {
        ResultActions resultActions = postPreviewEndpoint(inputMailContents, url);
        resultActions.andExpect(view().name("preview"))
                .andExpect(model().attribute("mailInfo", expectedMailInfo))
                .andExpect(model().attribute("previewNavigation", expectedPreviewNavigation));
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
