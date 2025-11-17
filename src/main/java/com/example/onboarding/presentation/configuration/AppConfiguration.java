package com.example.onboarding.presentation.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@ConfigurationProperties(prefix = "tpb.hydro.onboarding")
@Data
@EnableAsync
public class AppConfiguration {
    private String branchCode;
    private String branchName;
    private String saveFile;

    private Integer maxPoolSize;
    private Integer maxExecutorThread;
    private Integer threadQueueCapacity;

    private String thresholdScoreFaceMatch;
    private String thresholdScoreLiveness;
    private String thresholdScoreRetrival;

    private String folderSaveFile;

    @Bean("onboardingAsync")
    public TaskExecutor onboardingAsyncExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(getMaxPoolSize()); // Số lượng thread tối thiểu trong pool luôn hoạt động, dù không có task thread vẫn được giữ
        executor.setMaxPoolSize(getMaxExecutorThread()); // Số lượng thread tối đã có thể tạo ra, nếu vượt quá thì các task sẽ được đưa vào hàng đợi (queue)
        executor.setQueueCapacity(getThreadQueueCapacity()); // Dung lượng của hàng đợi các task chờ, nếu full sẽ tạo thêm thread (nếu chưa đến maxPoolSize)
        executor.setWaitForTasksToCompleteOnShutdown(true); // Khi shutdown executor, nó sẽ chờ các task chạy hoàn tất thay vì tắt ngay lập tức
        executor.setThreadNamePrefix("Async-onboardingAsync");
        return executor;
    }
}
