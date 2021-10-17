package com.github.msl.kafka.producer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.msl.spring.kafka.producer.service.InstallationKafkaProducer;
import com.verisure.vcp.sbn.avro.InstallationDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class InstallationsProducerTest {

	@Autowired
	private InstallationKafkaProducer producer;

	@Test
	public void testProduce() {
		log.info("Producing records in INSTALLATIONS.");
		for(int i=0; i<1; i++) {
			InstallationDTO installation = InstallationDTO.newBuilder().setADDR("ADDR").setADDRTP(i).setALAT("ALAT").setALIASNAME("test" + i).build();
			producer.sendRecord(i + "", installation);
		}
		log.info("END of Producing records in INSTALLATIONS.");

	}
		
}
