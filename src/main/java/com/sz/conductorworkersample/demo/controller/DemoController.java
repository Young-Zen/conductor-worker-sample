package com.sz.conductorworkersample.demo.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sz.conductorworkersample.demo.service.DemoService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Yanghj
 * @date 2024/2/2 9:40
 */
@Slf4j
@RestController
public class DemoController {

    @Resource private DemoService demoService;

    @RequestMapping("/")
    public String home() {
        log.info("Handling home");
        return "Hello World";
    }

    @GetMapping("/async")
    public String async() {
        log.info("Hello async at controller");
        demoService.async();
        return "Hello Async";
    }
}
