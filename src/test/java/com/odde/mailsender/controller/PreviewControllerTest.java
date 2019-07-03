package com.odde.mailsender.controller;

import com.odde.mailsender.data.AddressBook;
import com.odde.mailsender.data.AddressItem;
import com.odde.mailsender.service.AddressBookService;
import com.odde.mailsender.service.MailService;
import com.odde.mailsender.service.PreviewRequest;
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

import static com.odde.mailsender.service.PreviewBuilder.validPreview;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
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

        PreviewRequest previewRequest = validPreview().withSubject("Hello name").withBody("Hi name").withTo("foo@gmx.com").build();

        MvcResult resultActions = mvc.perform(post("/preview")
                .param("address", previewRequest.getTo())
                .param("subject", previewRequest.getSubject())
                .param("body", previewRequest.getBody()))
                .andExpect(view().name("preview"))
                .andExpect(model().attribute("address", "foo@gmx.com"))
                .andExpect(model().attribute("subject", "Hello name"))
                .andExpect(model().attribute("body", "Hi name"))
                .andReturn();

        //verify(mailService, times(1)).preview(null);
    }

    @Test
    public void showPreviewPageWithParam() throws Exception {

        addressBookService.add(new AddressItem("eventspark@gmx.com", "Aki"));

        PreviewRequest previewRequest = validPreview().withSubject("Hello $name").withBody("Hi $name").withTo("eventspark@gmx.com").build();

        MvcResult resultActions = mvc.perform(post("/preview")
                .param("address", previewRequest.getTo())
                .param("subject", previewRequest.getSubject())
                .param("body", previewRequest.getBody()))
                .andExpect(view().name("preview"))
                .andExpect(model().attribute("address", "eventspark@gmx.com"))
                .andExpect(model().attribute("subject", "Hello Aki"))
                .andExpect(model().attribute("body", "Hi Aki"))
                .andReturn();

        //verify(mailService, times(1)).preview(null);
    }

    @Test
    public void testAddressRequired() throws Exception {

        PreviewRequest previewRequest = validPreview().withSubject("Hello $name").withBody("Hi $name").withTo("eventspark@gmx.com;stanly@xxx.com").build();

        MvcResult resultActions = mvc.perform(post("/preview")
                .param("subject", previewRequest.getSubject())
                .param("body", previewRequest.getBody()))
                .andExpect(view().name("preview"))
                .andReturn();

        assertErrorMessage(resultActions, "address", "{0} may not be empty");
        verify(mailService, never()).preview(any());
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
