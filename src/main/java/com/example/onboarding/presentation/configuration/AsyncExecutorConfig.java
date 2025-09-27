package com.example.onboarding.presentation.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncExecutorConfig {
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);               // Số lượng thread core
        executor.setMaxPoolSize(10);                // Số lượng thread tối đa
        executor.setQueueCapacity(10);             // Sức chứa hàng đợi
        executor.setThreadNamePrefix("async-aaaaa");     // Prefix để log dễ theo dõi
        executor.initialize();
        return executor;
    }
}
