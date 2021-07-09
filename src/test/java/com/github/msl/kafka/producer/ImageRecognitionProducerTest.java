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
import com.verisure.advmon.image.Result;

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
			Bbox bbox = Bbox.newBuilder().setH(167.119140625).setW(86.74021911621094).setX(263.0750732421875).setY(171.4024200439453).build();
			Result result = Result.newBuilder().setBbox(bbox).setScore(91.80992841720581).setType("person").build();
			List<Result> results = new ArrayList<Result>();
			results.add(result);
			Image image = Image.newBuilder().setId(i +"").setResults(results).build();
			List<Image> images = new ArrayList<>();
			images.add(image);
			AnalysisResult analysisResult = AnalysisResult.newBuilder().setInstallationId(777777).setPosese("PRUEBA-MSL-TEST").setSei("036659950103202100030800").setId(i +"").setZoneId("YR02").setImages(images).build();

			producer.sendRecord(Integer.valueOf(i), analysisResult);
		}
	}

}
