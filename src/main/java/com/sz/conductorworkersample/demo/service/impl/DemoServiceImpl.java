package com.sz.conductorworkersample.demo.service.impl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sz.conductorworkersample.demo.service.DemoService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Yanghj
 * @date 2024/2/29 16:07
 */
@Slf4j
@Service
public class DemoServiceImpl implements DemoService {

    @Async
    @Override
    public void async() {
        log.info("Hello async at service");
    }
}
