package com.github.msl.spring.kafka;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

import com.github.msl.spring.kafka.producer.service.ImageRecognitionResultKafkaProducer;
import com.verisure.advmon.image.AnalysisResult;
import com.verisure.advmon.image.Bbox;
import com.verisure.advmon.image.Image;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableKafka
public class ProducerCommandLineRunner implements CommandLineRunner {


	@Autowired
	private ImageRecognitionResultKafkaProducer producerImageProcessingResult;
	
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
			AnalysisResult analysisResult = AnalysisResult.newBuilder().setIdInstallation("7777").setPosese("PRUEBA-MSL").setSei("1234567890").setId(i +"").setDevice("PANEL").setImages(images).build();
			producerImageProcessingResult.sendRecordWithResult(String.valueOf(i), analysisResult);
		}
	}
	

    @Override
    public void run(String... args) throws Exception {
		log.info("Running.");
		testProduceImageProcessingResult();
		log.info("Stopping.");

    }
}
