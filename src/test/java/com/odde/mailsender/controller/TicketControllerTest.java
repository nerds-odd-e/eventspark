package com.odde.mailsender.controller;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TicketControllerTest {

    @Test
    public void getIndexHtmlPage() {
        String ret = new TicketController().add("test");
        assertEquals("add-ticket", ret);
    }
}