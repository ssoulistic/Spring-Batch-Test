package com.seek.config;

import com.seek.job.BusStationJob;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job busStationJob() {
        return jobBuilderFactory.get("busStationJob")
                .incrementer(new RunIdIncrementer())
                .flow(busStationStep())
                .end()
                .build();
    }

    @Bean
    public Step busStationStep() {
        return stepBuilderFactory.get("busStationStep")
                .tasklet(busStationTasklet())
                .build();
    }

    @Bean
    public Tasklet busStationTasklet() {
        return (contribution, chunkContext) -> {
            // 여기에 API 호출 로직을 넣는다.
            System.out.println("API 호출 작업을 실행합니다...");
            BusStationJob busStationJob = null;
            busStationJob.createTasklet();
            // 예: API 호출 로직

            return RepeatStatus.FINISHED;
        };
    }
}
