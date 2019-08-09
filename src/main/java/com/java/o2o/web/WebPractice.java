package com.java.o2o.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/response")
public class WebPractice {
    @RequestMapping("/c2c.action")
    @ResponseBody
    public String he()
    {
        return "200";
    }
}
