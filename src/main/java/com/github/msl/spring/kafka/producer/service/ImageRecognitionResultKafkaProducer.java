package com.github.msl.spring.kafka.producer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.github.msl.spring.kafka.producer.TestKafkaProducerConfig;
import com.verisure.advmon.image.AnalysisResult;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ImageRecognitionResultKafkaProducer {

	@Autowired
	private KafkaTemplate<Integer, AnalysisResult> analysisResultTemplate;

	@Autowired
	TestKafkaProducerConfig config;

	public void sendRecord(Integer key, AnalysisResult analysisResult) {
		log.info("Sending message to topic " + config.getEventProducerTopicName() + " with key=[" + key + ", value=["
				+ analysisResult + "]");
		analysisResultTemplate.send(config.getEventProducerTopicName(), key, analysisResult);
	}

	public void sendRecordWithResult(Integer key, AnalysisResult analysisResult) {
		log.info("Sending message to topic " + config.getEventProducerTopicName() + "  with key=[" + key + ", value=["
				+ analysisResult + "]");

		ListenableFuture<SendResult<Integer, AnalysisResult>> future = analysisResultTemplate
				.send(config.getEventProducerTopicName(), key, analysisResult);
		future.addCallback(new ListenableFutureCallback<SendResult<Integer, AnalysisResult>>() {

			@Override
			public void onSuccess(SendResult<Integer, AnalysisResult> result) {
				log.info("Sent message with key=[" + key + ", value=[" + analysisResult + "], offset=["
						+ result.getRecordMetadata().offset() + "]");
			}

			@Override
			public void onFailure(Throwable ex) {
				log.error("Unable to send message=[" + analysisResult + "] due to : " + ex.getMessage());
			}
		});
	}
}