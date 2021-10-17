package com.github.msl.spring.kafka.producer.service;

import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
@ConfigurationProperties(prefix = "kafka")
public class KafkaProducerConfig {

	@NotBlank
	private String topicImages;
	
	@NotBlank
	private String topicInstallations; 
}
