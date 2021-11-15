package com.xt37.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MarkDown {

    @GetMapping("markDown")
    public String markDown(){
        return "MarkDown";
    }
}
