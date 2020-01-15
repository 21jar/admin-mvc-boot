package com.ixiangliu.controller;

import com.ixiangliu.common.utils.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/http")
public class httpController {

    /**
     * 列表
     */
    @RequestMapping("/list")
    public Result list(){
        System.out.println("111");
        return Result.ok("success");
    }

}
