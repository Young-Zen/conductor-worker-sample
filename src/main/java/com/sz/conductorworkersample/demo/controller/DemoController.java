package com.sz.conductorworkersample.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Yanghj
 * @date 2024/2/2 9:40
 */
@Slf4j
@RestController
public class DemoController {

    @RequestMapping("/")
    public String home() {
        log.info("Handling home");
        return "Hello World";
    }
}
