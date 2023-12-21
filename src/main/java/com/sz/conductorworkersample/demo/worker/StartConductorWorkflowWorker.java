package com.sz.conductorworkersample.demo.worker;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.netflix.conductor.client.http.WorkflowClient;
import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;

import com.sz.conductorworkersample.demo.config.WorkerProperties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class StartConductorWorkflowWorker implements Worker {

    @Resource private WorkerProperties workerProperties;
    @Resource private WorkflowClient workflowClient;

    @Override
    public String getTaskDefName() {
        return "DEMO_START_CONDUCTOR_WORKFLOW";
    }

    @Override
    public TaskResult execute(Task task) {
        log.info(
                "WorkflowInstanceId: {}, TaskId: {}, Type: {}, TDN: {}",
                task.getWorkflowInstanceId(),
                task.getTaskId(),
                task.getTaskType(),
                task.getTaskDefName());

        String workflowName = (String) task.getInputData().get("workflowName");

        StartWorkflowRequest startWorkflowRequest = new StartWorkflowRequest();
        startWorkflowRequest.setName(workflowName);
        String response = workflowClient.startWorkflow(startWorkflowRequest);

        TaskResult taskResult = new TaskResult(task);
        taskResult.getOutputData().put("workflowExecutionId", response);
        taskResult.setStatus(TaskResult.Status.COMPLETED);
        return taskResult;
    }

    @Override
    public int getPollingInterval() {
        if (workerProperties.getPollingInterval() > 0) {
            return workerProperties.getPollingInterval();
        }
        return Worker.super.getPollingInterval();
    }
}
