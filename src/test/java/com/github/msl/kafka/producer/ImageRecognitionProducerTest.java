package com.github.msl.kafka.producer;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.msl.spring.kafka.producer.service.ImageRecognitionResultKafkaProducer;
import com.verisure.advmon.image.AnalysisResult;
import com.verisure.advmon.image.Bbox;
import com.verisure.advmon.image.Image;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ImageRecognitionProducerTest {

	@Autowired
	private ImageRecognitionResultKafkaProducer producer;

	@Test
	public void testProduce() {
		log.info("Producing record.");
		for(int i=0; i<10; i++) {
			Bbox bbox = Bbox.newBuilder().setH(1).setW(10).setX(1).setY(2).build();
			Image image = Image.newBuilder().setId(i +"").setBbox(bbox).setScore(99).setType("person").build();
			List<Image> images = new ArrayList<>();
			images.add(image);
			AnalysisResult analysisResult = AnalysisResult.newBuilder().setIdInstallation("7777").setPosese("PRUEBA-MSL").setSei("1234567890").setId(i +"").setDevice("PANEL").setImages(images).build();
			producer.sendRecord(String.valueOf(i), analysisResult);
		}
	}

}
