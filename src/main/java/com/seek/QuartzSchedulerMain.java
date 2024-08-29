package com.seek;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import com.seek.scheduler.QuartzJob;

public class QuartzSchedulerMain {
    public static void main(String[] args) {
        try {
            JobDetail jobDetail = JobBuilder.newJob(QuartzJob.class)
                    .withIdentity("busStationJob")
                    .build();

            // 매일 자정에 실행되도록 설정
//            Trigger trigger = TriggerBuilder.newTrigger()
//                    .withIdentity("busStationTrigger")
//                    .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(0, 0))
//                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("busStationTrigger")
                    .withSchedule(CronScheduleBuilder.cronSchedule("*/10 * * * * ?"))
                    .build();

            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
