package com.sz.conductorworkersample.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * @author Yanghj
 * @date 2023/12/21 19:32
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "conductor.worker.all")
public class WorkerProperties {

    private String domain;

    private int pollingInterval = 1000;

    private String username;

    private String password;

    @SuppressWarnings("PMD")
    private int workflowInputPayloadThresholdKB = 5120;

    @SuppressWarnings("PMD")
    private int taskOutputPayloadThresholdKB = 3072;
}
