package com.sz.conductorworkersample.demo.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import io.micrometer.context.ContextExecutorService;
import io.micrometer.context.ContextSnapshot;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Yanghj
 * @date 2024/2/29 16:36
 */
@Slf4j
@Configuration
public class AsyncExecutorConfigurer extends AsyncConfigurerSupport {

    @Value("${custom.async-executor.core-pool-size:4}")
    private int corePoolSize;

    @Value("${custom.async-executor.max-pool-size:8}")
    private int maxPoolSize;

    @Value("${custom.async-executor.queue-capacity:64}")
    private int queueCapacity;

    @Value("${custom.async-executor.keep-alive-seconds:300}")
    private int keepAliveSeconds;

    @Override
    public Executor getAsyncExecutor() {
        log.info("start asyncServiceExecutor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix("async-service-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        // https://stackoverflow.com/questions/75401265/spring-boot-3-taskexecutor-context-propagation-in-micrometer-tracing
        return ContextExecutorService.wrap(
                executor.getThreadPoolExecutor(), ContextSnapshot::captureAll);
    }
}
