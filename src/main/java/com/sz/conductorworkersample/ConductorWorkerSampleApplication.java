package com.sz.conductorworkersample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import com.netflix.conductor.client.spring.ConductorClientAutoConfiguration;
import com.netflix.conductor.contribs.metrics.PrometheusMetricsConfiguration;

/**
 * @author Yanghj
 * @date 2023/12/21 19:32
 */
@EnableAsync
@Import({PrometheusMetricsConfiguration.class, ConductorClientAutoConfiguration.class})
@SpringBootApplication
public class ConductorWorkerSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConductorWorkerSampleApplication.class, args);
    }
}
