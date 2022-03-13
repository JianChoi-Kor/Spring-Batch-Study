package com.study.springbatch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
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
public class CustomExitStatusJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job customExitStatusJob() {
        return jobBuilderFactory.get("customExitStatusJob")
                .start(customExitStatusStep1())
                    .on("FAILED")
                    .end()
                .from(customExitStatusStep1())
                    .on("COMPLETED WITH SKIPS")
                    .to(errorPrint1())
                    .on("*")
                    .end()
                .from(customExitStatusStep1())
                    .on("*")
                    .to(customExitStatusStep2())
                    .on("*")
                    .end()
                .end()
                .build();
    }

    @Bean
    public Step customExitStatusStep1() {
        return stepBuilderFactory.get("customExitStatusStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> This is stepNextConditionalJob Step1");
                    contribution.setExitStatus(new ExitStatus("COMPLETED WITH SKIPS"));

                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step customExitStatusStep2() {
        return stepBuilderFactory.get("customExitStatusStep2")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> This is stepNextConditionalJob Step2");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step errorPrint1() {
        return stepBuilderFactory.get("errorPrint1")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> Error Print1");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
