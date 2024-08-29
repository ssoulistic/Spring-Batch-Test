package com.seek.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.seek.config.BatchConfig;

public class QuartzJob implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        try (AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(BatchConfig.class)) {
            JobLauncher jobLauncher = appContext.getBean(JobLauncher.class);
            org.springframework.batch.core.Job job = appContext.getBean("busStationJob", org.springframework.batch.core.Job.class);

            jobLauncher.run(job, new JobParameters());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
