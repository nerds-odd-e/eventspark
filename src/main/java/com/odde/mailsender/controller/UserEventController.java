package com.odde.mailsender.controller;

import com.odde.mailsender.bean.UserEventListBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserEventController {

    @GetMapping("/event")
    public ModelAndView list() {
        Map<String, Object> model = new HashMap<>();
        model.put("bean", getUserEventListBean());
        return new ModelAndView("event-list", model);
    }

    private UserEventListBean getUserEventListBean() {
        List<UserEventListBean.Event> eventList = new ArrayList<>();
        eventList.add(UserEventListBean.Event.builder()
                .title("sample")
                .build());
        eventList.add(UserEventListBean.Event.builder()
                .title("sample")
                .build());
        return UserEventListBean.builder()
                .eventList(eventList)
                .build();
    }
}
