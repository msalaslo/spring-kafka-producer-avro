package com.github.msl.spring.kafka;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

import com.github.msl.spring.kafka.producer.service.ImageRecognitionResultKafkaProducer;
import com.github.msl.spring.kafka.producer.service.IntegrationSpikeKafkaProducer;
import com.verisure.advmon.image.AnalysisResult;
import com.verisure.advmon.image.Bbox;
import com.verisure.advmon.image.Image;
import com.verisure.spike.TestDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableKafka
public class ProducerCommandLineRunner implements CommandLineRunner {


	@Autowired
	private ImageRecognitionResultKafkaProducer producerImageProcessingResult;
	
	@Autowired
	private IntegrationSpikeKafkaProducer producerIntegrationSpike;
	
    public static void main(String[] args) {
        log.info("STARTING THE APPLICATION");
        SpringApplication.run(ProducerCommandLineRunner.class, args);
        log.info("APPLICATION FINISHED");
    }

	public void testProduceImageProcessingResult() {
		log.info("Producing record in image result.");
		for(int i=0; i<10; i++) {
			Bbox bbox = Bbox.newBuilder().setH(1).setW(10).setX(1).setY(2).build();
			Image image = Image.newBuilder().setId(i +"").setBbox(bbox).setScore(99).setType("person").build();
			List<Image> images = new ArrayList<>();
			images.add(image);
			AnalysisResult analysisResult = AnalysisResult.newBuilder().setId(i +"").setImages(images).build();
			producerImageProcessingResult.sendRecordWithResult(Integer.valueOf(i), analysisResult);
		}
	}
	
	public void testProduceIntegrationSpike() {
		log.info("Producing record.");
		for(int i=0; i<10; i++) {
			TestDTO testDto = TestDTO.newBuilder().setMyField1(1).setMyField2(1200).setMyField3("test from java").build();
			producerIntegrationSpike.sendRecord("key", testDto);
		}
	}

    @Override
    public void run(String... args) throws Exception {
		log.info("Running.");
		testProduceImageProcessingResult();
		testProduceIntegrationSpike();
		log.info("Stopping.");

    }
}
