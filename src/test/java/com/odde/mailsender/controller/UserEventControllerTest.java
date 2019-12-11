package com.odde.mailsender.controller;

import org.junit.Assert;
import org.junit.Test;

public class UserEventControllerTest {

    @Test
    public void getListPage() {
        String ret = new UserEventController().list();
        Assert.assertEquals("event-list",ret);
    }

}
