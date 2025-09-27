package com.example.onboarding.presentation.scheduler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
@EnableScheduling
public class SdkConfigScheduler {

    @Value("${job-schedule.limit}")
    private String limit;

    @Value("${job-schedule.offset}")
    public String offSet;

    @Value("${job-schedule.fixedDelay}")
    public String fixedDelay;

    @Value("${job-schedule.registerBranch}")
    public String registerBranch;

    @Value("${job-schedule.turnOn}")
    public boolean turnOn;

    private final ThreadPoolExecutor executor;

    public SdkConfigScheduler(@Value("${job-schedule.threadPool}") int threadPool) {
        executor = new ThreadPoolExecutor(threadPool, threadPool, 0, TimeUnit.MICROSECONDS, new LinkedBlockingDeque<>());
    }

    @Scheduled(cron = "${job-schedule.cron.expression}")
    public void loadSdkConfig(){
        System.out.println("====chay job cron===");
    }
}
