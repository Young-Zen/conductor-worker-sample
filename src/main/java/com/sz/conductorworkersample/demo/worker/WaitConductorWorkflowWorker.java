package com.sz.conductorworkersample.demo.worker;

import com.netflix.conductor.client.http.WorkflowClient;
import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.run.Workflow;
import com.sz.conductorworkersample.demo.config.WorkerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class WaitConductorWorkflowWorker implements Worker {

    @Resource
    private WorkerProperties workerProperties;
    @Resource
    private WorkflowClient workflowClient;

    @Override
    public String getTaskDefName() {
        return "DEMO_WAIT_CONDUCTOR_WORKFLOW";
    }

    @Override
    public TaskResult execute(Task task) {
        log.info("WorkflowInstanceId: {}, TaskId: {}, Type: {}, TDN: {}", task.getWorkflowInstanceId(), task.getTaskId(), task.getTaskType(), task.getTaskDefName());

        String workflowExecutionId = (String) task.getInputData().get("workflowExecutionId");

        Workflow workflow = workflowClient.getWorkflow(workflowExecutionId, false);

        TaskResult taskResult = new TaskResult(task);
        taskResult.setStatus(TaskResult.Status.IN_PROGRESS);
        if (workflow.getStatus() == Workflow.WorkflowStatus.RUNNING || workflow.getStatus() == Workflow.WorkflowStatus.PAUSED) {
            taskResult.setCallbackAfterSeconds(10);
        } else if (workflow.getStatus() == Workflow.WorkflowStatus.COMPLETED) {
            taskResult.setStatus(TaskResult.Status.COMPLETED);
        } else if (workflow.getStatus() == Workflow.WorkflowStatus.TERMINATED) {
            taskResult.setStatus(TaskResult.Status.FAILED_WITH_TERMINAL_ERROR);
            taskResult.setReasonForIncompletion(String.format("Workflow %s execute failed: %s", workflowExecutionId, workflow.getReasonForIncompletion()));
        } else {
            taskResult.setStatus(TaskResult.Status.FAILED);
            taskResult.setReasonForIncompletion(String.format("Workflow %s execute failed: %s", workflowExecutionId, workflow.getReasonForIncompletion()));
        }
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
