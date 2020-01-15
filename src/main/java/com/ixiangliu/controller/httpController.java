package com.ixiangliu.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/http")
public class httpController {

    /**
     * 列表
     */
    @RequestMapping("/list")
    public void list(){
        System.out.println("111");
    }

}
