package com.study.springbatch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SchedulerJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job schedulerJob() {
        return jobBuilderFactory.get("schedulerJob")
                .start(schedulerJobStep())
                .build();
    }

    @Bean
    public Step schedulerJobStep() {
        return stepBuilderFactory.get("schedulerJobStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> schedulerJobStep");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
