package com.sz.conductorworkersample.demo.config;

import com.netflix.conductor.client.config.ConductorClientConfiguration;
import com.netflix.conductor.client.config.DefaultConductorClientConfiguration;
import com.netflix.conductor.client.http.TaskClient;
import com.netflix.conductor.client.http.WorkflowClient;
import com.netflix.conductor.client.spring.ClientProperties;
import com.sun.jersey.api.client.ClientHandler;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.ClientFilter;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class WorkerConfig {

    @Bean
    public WorkflowClient workflowClient(ClientProperties clientProperties, WorkerProperties workerProperties) {
        WorkflowClient workflowClient = new WorkflowClient(new DefaultClientConfig(),
                this.getConductorClientConfiguration(workerProperties),
                null,
                this.getClientHTTPBasicAuthFilter(workerProperties));
        workflowClient.setRootURI(clientProperties.getRootUri());
        return workflowClient;
    }

    @Bean
    public TaskClient taskClient(ClientProperties clientProperties, WorkerProperties workerProperties) {
        TaskClient taskClient = new TaskClient(new DefaultClientConfig(),
                this.getConductorClientConfiguration(workerProperties),
                null,
                this.getClientHTTPBasicAuthFilter(workerProperties));
        taskClient.setRootURI(clientProperties.getRootUri());
        return taskClient;
    }

    private ClientFilter[] getClientHTTPBasicAuthFilter(WorkerProperties workerProperties) {
        if (StringUtils.hasText(workerProperties.getUsername()) && StringUtils.hasText(workerProperties.getPassword())) {
            return new HTTPBasicAuthFilter[]{new HTTPBasicAuthFilter(workerProperties.getUsername(), workerProperties.getPassword())};
        }
        return new ClientFilter[0];
    }

    private ConductorClientConfiguration getConductorClientConfiguration(WorkerProperties workerProperties) {
        return new CustomConductorClientConfiguration(workerProperties.getWorkflowInputPayloadThresholdKB(), workerProperties.getTaskOutputPayloadThresholdKB());
    }

    @AllArgsConstructor
    class CustomConductorClientConfiguration extends DefaultConductorClientConfiguration {

        private int workflowInputPayloadThresholdKB;

        private int taskOutputPayloadThresholdKB;

        @Override
        public int getWorkflowInputPayloadThresholdKB() {
            return workflowInputPayloadThresholdKB;
        }

        @Override
        public int getTaskOutputPayloadThresholdKB() {
            return taskOutputPayloadThresholdKB;
        }
    }
}
