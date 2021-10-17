package com.github.msl.spring.kafka.producer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.verisure.vcp.sbn.avro.InstallationDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InstallationKafkaProducer {

	@Autowired
	private KafkaTemplate<String, InstallationDTO> kafkaTemplate;

	@Autowired
	private KafkaProducerConfig kafkaProducerConfig;

	public void sendRecord(String key, InstallationDTO installation) {
		log.info("Sending message to topic " + kafkaProducerConfig.getTopicInstallations() + " with key=[" + key + ", value=[" + installation + "]");
		kafkaTemplate.send(kafkaProducerConfig.getTopicInstallations(), key, installation);
	}

}