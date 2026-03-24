package com.framwork.core.batch;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzSchedulerConfig {

    @Bean
    public JobDetail batchJobDetail() {
        return JobBuilder.newJob(QuartzBatchLauncherJob.class)
                .withIdentity("sampleBatchLauncherJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger batchJobTrigger(
            JobDetail batchJobDetail,
            @Value("${framework.batch.cron:0 0/5 * * * ?}") String cronExpression
    ) {
        return TriggerBuilder.newTrigger()
                .forJob(batchJobDetail)
                .withIdentity("sampleBatchLauncherTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();
    }
}
