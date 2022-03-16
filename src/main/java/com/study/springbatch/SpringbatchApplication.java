package com.study.springbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableBatchProcessing // 배치기능 활성화
@SpringBootApplication
public class SpringbatchApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringbatchApplication.class, args);
	}
}
