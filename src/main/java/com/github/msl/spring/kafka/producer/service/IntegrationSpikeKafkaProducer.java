package com.github.msl.spring.kafka.producer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.github.msl.spring.kafka.producer.TestKafkaProducerConfig;
import com.verisure.spike.TestDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IntegrationSpikeKafkaProducer {

	@Autowired
	private KafkaTemplate<String, TestDTO> integrationSpikeKafkaTemplate;

	@Autowired
	TestKafkaProducerConfig config;

	public void sendRecord(String key, TestDTO testDto) {
		log.info("Sending message to topic " + config.getIntegrationSpikeTopicName() + " with key=[" + key + ", value=["
				+ testDto + "]");
		integrationSpikeKafkaTemplate.send(config.getIntegrationSpikeTopicName(), key, testDto);
	}
}