package com.sz.conductorworkersample.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "conductor.worker.all")
public class WorkerProperties {

    private String domain;

    private int pollingInterval = 1000;

    private String username;

    private String password;

    private int workflowInputPayloadThresholdKB = 5120;

    private int taskOutputPayloadThresholdKB = 3072;
}
