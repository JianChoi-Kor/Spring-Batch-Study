package com.study.springbatch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class TestJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job testJob() {
        return jobBuilderFactory.get("testJob") // testJob 이라는 이름의 Batch Job을 생성합니다.
                .start(testStep1(null))
                .next(testStep2(null))
                .next(testStep3())
                .build();
    }

    @Bean
    @JobScope
    public Step testStep1(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return stepBuilderFactory.get("testStep1") // testOneStep1 이라는 이름의 Batch Step을 생성합니다.
                .tasklet((contribution, chunkContext) -> { // Tasklet은 Step 안에서 단일로 수행될 커스텀한 기능들을 선언할 떄 사용합니다.
                    log.info(">>>>> This is Step1");
                    log.info(">>>>> requestDate = {}", requestDate);
                    return RepeatStatus.FINISHED;
//                    throw new IllegalArgumentException("step1에서 실패합니다.");
                })
                .build();
    }

    @Bean
    @JobScope
    public Step testStep2(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return stepBuilderFactory.get("testStep2") // testOneStep1 이라는 이름의 Batch Step을 생성합니다.
                .tasklet((contribution, chunkContext) -> { // Tasklet은 Step 안에서 단일로 수행될 커스텀한 기능들을 선언할 떄 사용합니다.
                    log.info(">>>>> This is Step2");
                    log.info(">>>>> requestDate = {}", requestDate);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step testStep3() {
        return stepBuilderFactory.get("testStep3")
                .tasklet(testStep3Tasklet(null))
                .build();
    }

    @Bean
    @StepScope
    public Tasklet testStep3Tasklet(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return (contribution, chunkContext) -> {
            log.info(">>>>> This is testStep3");
            log.info(">>>>> requestDate = {}", requestDate);
            return RepeatStatus.FINISHED;
        };
    }
}
