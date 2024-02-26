package com.sz.conductorworkersample.demo.aop;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.netflix.conductor.common.metadata.tasks.Task;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;

/**
 * @author Yanghj
 * @date 2024/2/2 11:08
 */
@Aspect
@Order(0)
@Component
public class AopForWorker {

    @Resource private Tracer tracer;

    @Pointcut("execution(* com.netflix.conductor.client.worker.Worker.execute(..))")
    public void workerAop() {}

    @Around("workerAop()")
    public Object methodAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Task task = (Task) joinPoint.getArgs()[0];
        Span newSpan = this.tracer.nextSpan().name(task.getTaskType());
        try (Tracer.SpanInScope ws = tracer.withSpan(newSpan.start())) {
            return joinPoint.proceed();
        } finally {
            newSpan.end();
        }
    }
}
