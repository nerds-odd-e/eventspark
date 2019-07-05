package com.odde.mailsender.controller;

import com.odde.mailsender.data.AddressBook;
import com.odde.mailsender.service.*;
import com.odde.mailsender.data.AddressItem;
import org.apache.commons.mail.EmailException;
import org.junit.Before;
import org.junit.Ignore;
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
import java.util.*;
import java.util.stream.Stream;

import static com.odde.mailsender.service.MailBuilder.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MailControllerTest {

    @MockBean
    private MailService mailService;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AddressBookService addressBookService;

    private static final AddressItem aki = new AddressItem("eventspark@gmx.com", "Aki");
    private static final AddressItem stanly = new AddressItem("stanly@xxx.com", "Stanly");
    private static final AddressItem noNameAddress = new AddressItem("noname@gmx.com", "");

    @Before
    public void setUp() {
        when(addressBookService.getAddressItems(new String[]{aki.getMailAddress(), stanly.getMailAddress()}))
                .thenReturn(Arrays.asList(aki, stanly));

        when(addressBookService.getAddressItems(new String[]{noNameAddress.getMailAddress()}))
                .thenReturn(Collections.singletonList(noNameAddress));

        when(addressBookService.getAddressItems(new String[]{"foobar@xxx.com"}))
                .thenReturn(Collections.singletonList(null));
    }

    @Test
    public void goToHome() throws Exception {
        mvc.perform(get(""))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    public void sendToMultipleAddresses() throws Exception {
        getPerform(validMail().withTo("abc@gmail.com;john@gmail.com").build())
                .andExpect(view().name("redirect:/home"));

        verify(mailService, times(1)).sendMultiple(any());
    }

    @Test
    public void showErrorIfEmptyForms() throws Exception {
        MvcResult mvcResult = getPerform(validMail().withTo("").withSubject("").withBody("").build())
                .andExpect(view().name("home"))
                .andReturn();

        assertErrorMessage(mvcResult, "address", "{0} may not be empty");
        assertErrorMessage(mvcResult, "subject", "{0} may not be empty");
        assertErrorMessage(mvcResult, "body", "{0} may not be empty");
        verify(mailService, never()).sendMultiple(any());
    }

    @Test
    public void manyAddressWithInvalidAddressAndNoSubject() throws Exception {
        MvcResult mvcResult = getPerform(validMail().withTo("abcdefghi123@xxx.com ; xxx.com; stanly@xxx.com").withSubject("").build())
                .andExpect(view().name("home"))
                .andReturn();

        assertErrorMessage(mvcResult, "address", "Address format is wrong");
        assertErrorMessage(mvcResult, "subject", "{0} may not be empty");
        verify(mailService, never()).sendMultiple(any());
    }

    @Test
    public void sendMultipleWhenUseTemplate() throws Exception {
        MailInfo mailInfo = validMail().withSubject("Hello $name").withBody("Hi $name").withTo("eventspark@gmx.com;stanly@xxx.com").build();

        getPerform(mailInfo)
                .andExpect(view().name("redirect:/home"));

        verify(mailService).sendMultiple(argThat(mailInfoList -> mailInfoList.get(0).getSubject().equals("Hello Aki")));
        verify(mailService).sendMultiple(argThat(mailInfoList -> mailInfoList.get(1).getSubject().equals("Hello Stanly")));
        verify(mailService).sendMultiple(argThat(mailInfoList -> mailInfoList.get(0).getBody().equals("Hi Aki")));
        verify(mailService).sendMultiple(argThat(mailInfoList -> mailInfoList.get(1).getBody().equals("Hi Stanly")));
    }

    @Test
    public void notSubjectReplaceWhenNotRegisteredAddress() throws Exception {
        MvcResult mvcResult = getPerform(validMail().withSubject("Hello $name").withTo("foobar@xxx.com").build())
                .andExpect(view().name("home"))
                .andReturn();

        assertErrorMessage(mvcResult, "", "When you use template, choose email from contract list that has a name");
        verify(mailService, never()).sendMultiple(any());
    }

    @Test
    public void notBodyReplaceWhenNotRegisteredAddress() throws Exception {
        MvcResult mvcResult = getPerform(validMail().withBody("Hi $name").withTo("foobar@xxx.com").build())
                .andExpect(view().name("home"))
                .andReturn();

        assertErrorMessage(mvcResult, "", "When you use template, choose email from contract list that has a name");
        verify(mailService, never()).sendMultiple(any());
    }

    @Test
    public void notSubjectReplaceWhenNoNameAddress() throws Exception {
        MailInfo mailInfo = validMail().withSubject("Hi $name").withTo(noNameAddress.getMailAddress()).build();

        MvcResult mvcResult = getPerform(mailInfo)
                .andExpect(view().name("home")).andReturn();

        assertErrorMessage(mvcResult, "", "When you use template, choose email from contract list that has a name");
        verify(mailService, never()).sendMultiple(any());
    }

    @Test
    public void notBodyReplaceWhenNoNameAddress() throws Exception {
        MailInfo mailInfo = validMail().withBody("Hi $name").withTo(noNameAddress.getMailAddress()).build();

        MvcResult mvcResult = getPerform(mailInfo)
                .andExpect(view().name("home"))
                .andReturn();

        assertErrorMessage(mvcResult, "", "When you use template, choose email from contract list that has a name");
        verify(mailService, never()).sendMultiple(any());
    }

    @Test
    public void mailServerHasDown()  throws Exception {
        doThrow(new Exception("Try to send email, but failed")).when(mailService).sendMultiple(any(List.class));
        MvcResult mvcResult = getPerform(validMail().withTo("abc@gmail.com;john@gmail.com").build())
                .andExpect(view().name("home"))
                .andReturn();

        assertErrorMessage(mvcResult, "", "Try to send email, but failed");
        verify(mailService, times(1)).sendMultiple(any());
    }

    @Test
    public void postToHome() throws Exception {
        MvcResult mvcResult = mvc.perform(post("/home")
                .param("from", "")
                .param("address", "test@hoge.co.jp")
                .param("subject", "Hi $name")
                .param("body", "Hello $name"))
                .andExpect(view().name("home"))
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        assertTrue(contentAsString.contains("test@hoge.co.jp"));
        assertTrue(contentAsString.contains("Hi $name"));
        assertTrue(contentAsString.contains("Hello $name"));
    }

    @Test
    public void loadTemplate() throws Exception {
        String expectedSubject = "Hi $name, How are you?";
        String expectedBody = "Hello $name, How are you?";
        MailTemplate mailTemplate = new MailTemplate(expectedSubject, expectedBody);

        when(mailService.getTemplate()).thenReturn(mailTemplate);

        MvcResult mvcResult = mvc.perform(post("/load")
                .param("from", "")
                .param("address", "test@hoge.co.jp")
                .param("subject", "Hi $name")
                .param("body", "Hello $name"))
                .andExpect(view().name("home"))
                .andReturn();

        verify(mailService, times(1)).getTemplate();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        assertTrue(contentAsString.contains("test@hoge.co.jp"));
        assertTrue(contentAsString.contains(expectedSubject));
        assertTrue(contentAsString.contains(expectedBody));
    }


    private ResultActions getPerform(MailInfo mailInfo) throws Exception {
        return mvc.perform(post("/send")
                .param("from", mailInfo.getFrom())
                .param("address", mailInfo.getTo())
                .param("subject", mailInfo.getSubject())
                .param("body", mailInfo.getBody()));
    }

    private void assertErrorMessage(MvcResult mvcResult, String errorMessage, String errorTemplateMessage) {
        ModelAndView mav = mvcResult.getModelAndView();
        List<ObjectError> objectErrors = ((BindingResult) mav.getModel().get(
                "org.springframework.validation.BindingResult.form")).getAllErrors();


        if (objectErrors.stream().anyMatch(i -> i instanceof FieldError)) {
            assertTrue(objectErrors.stream().map(i -> ((FieldError) i))
                    .anyMatch(i -> i.getField().equals(errorMessage) && i.getDefaultMessage().equals(errorTemplateMessage)));
        } else {
            assertTrue(objectErrors.stream()
                    .anyMatch(i -> i.getDefaultMessage().equals(errorTemplateMessage)));
        }


    }
}
