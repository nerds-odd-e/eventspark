package com.odde.mailsender.controller;

import com.odde.mailsender.service.MailService;
import com.odde.mailsender.service.PreviewRequest;
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

import java.util.List;

import static com.odde.mailsender.service.PreviewBuilder.validPreview;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PreviewControllerTest {

    @MockBean
    private MailService mailService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void showPreviewPage() throws Exception {

        PreviewRequest previewRequest = validPreview().withSubject("Hello $name").withBody("Hi $name").withTo("eventspark@gmx.com;stanly@xxx.com").build();

        MvcResult resultActions = mvc.perform(post("/preview")
                .param("address", previewRequest.getTo())
                .param("subject", previewRequest.getSubject())
                .param("body", previewRequest.getBody()))
                .andExpect(view().name("preview"))
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
