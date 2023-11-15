package com.sz.conductorworkersample.demo.config;

import com.netflix.conductor.client.spring.ConductorClientAutoConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Component
public class ConductorWorkerDomainBeanPostProcessor implements BeanPostProcessor {

    @Resource
    private WorkerProperties workerProperties;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ConductorClientAutoConfiguration) {
            loadWorkerDomainConfig();
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    private void loadWorkerDomainConfig() {
        if (StringUtils.hasText(workerProperties.getDomain())) {
            System.setProperty("conductor.worker.all.domain", workerProperties.getDomain());
        }
    }
}
