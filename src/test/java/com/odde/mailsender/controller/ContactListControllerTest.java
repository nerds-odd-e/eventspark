package com.odde.mailsender.controller;

import com.odde.mailsender.data.Address;
import com.odde.mailsender.form.MailSendForm;
import com.odde.mailsender.service.AddressRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class ContactListControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private AddressRepository addressRepository;

    @Before
    public void setup() {
        addressRepository.deleteAll();
    }

    @Test
    public void getSize1ContactList() throws Exception {
        addressRepository.save(new Address("aaa","aaa@example.com"));

        mvc.perform(get("/contact-list"))
                .andExpect(model().attribute("contactList", hasSize(1)));
    }

    @Test
    public void addEmailAddress() throws Exception {
        performContactListSuccess("aaa@example.com", "aaa");

        List<Address> all = addressRepository.findAll();
        assertEquals(1, all.size());
        assertEquals("aaa", all.get(0).getName());
        assertEquals("aaa@example.com", all.get(0).getMailAddress());
    }

    @Test
    public void addEmptyEmailAddress() throws Exception {
        assertAddressErrorMessage(performContactListError(""), "{0} may not be empty");
    }

    @Test
    public void addNotValidEmailAddress() throws Exception {
        assertAddressErrorMessage(performContactListError("aaa"), "Address format is wrong");
    }

    @Test
    public void createMailEmpty() throws Exception {
        MvcResult mvcResult = mvc.perform(post("/create-mail"))
                .andExpect(view().name("home")).andReturn();

        assertEquals("", ((MailSendForm)mvcResult.getModelAndView().getModel().get("form")).getAddress());
    }

    @Test
    public void createMailTwo() throws Exception {
        MvcResult mvcResult = mvc.perform(post("/create-mail").param("mailAddress", "aaa@example.com", "bbb@example.com"))
                .andExpect(view().name("home")).andReturn();

        assertEquals("aaa@example.com;bbb@example.com", ((MailSendForm)mvcResult.getModelAndView().getModel().get("form")).getAddress());
    }

    private MvcResult performContactListSuccess(String addressValue, String nameValue) throws Exception {
        return performContactList(addressValue, nameValue, "redirect:/contact-list");
    }

    private MvcResult performContactListError(String addressValue) throws Exception {
        return performContactList(addressValue, "aaa", "contact-list");
    }

    private MvcResult performContactList(String addressValue, String nameValue, String expectedViewName) throws Exception {
        return mvc.perform(post("/contact-list")
                .param("address", addressValue)
                .param("name", nameValue))
                .andExpect(view().name(expectedViewName)).andReturn();
    }

    private void assertAddressErrorMessage(MvcResult mvcResult, String expectMessage) {
        BeanPropertyBindingResult result = (BeanPropertyBindingResult)mvcResult.getModelAndView().getModelMap().get("org.springframework.validation.BindingResult.form");
        assertEquals(expectMessage, result.getFieldError("address").getDefaultMessage());
    }
}
