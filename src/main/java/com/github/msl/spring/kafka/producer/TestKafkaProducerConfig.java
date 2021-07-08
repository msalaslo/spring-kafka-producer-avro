package com.github.msl.spring.kafka.producer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.verisure.advmon.image.AnalysisResult;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import lombok.Data;

@EnableKafka
@Configuration
@Data
public class TestKafkaProducerConfig {
	
    @Value(value = "${event.producer.topic.name}")
    private String eventProducerTopicName;
    
    @Value(value = "${integration.spike.topic.name}")
    private String integrationSpikeTopicName;
    
    @Value(value = "${spring.kafka.properties.bootstrap.servers}")
    private String producerBootstrapAddress;
       
	@Value(value = "${spring.kafka.properties.security.protocol}")
	private String producerSecurityProtocol;
	
    @Value(value = "${spring.kafka.properties.sasl.jaas.config}")
    private String producerSaslJaasConfig;
    
    @Value(value = "${spring.kafka.properties.sasl.mechanism}")
    private String producerSaslMechanism;
    
    @Value(value = "${spring.kafka.properties.schema.registry.url}")
    private String schemaRegistryUrl;
    
    @Value(value = "${spring.kafka.properties.basic.auth.credentials.source}")
    private String srAuthCredentialsSource;
    
    @Value(value = "${spring.kafka.properties.basic.auth.user.info}")
    private String srUserInfo;
    
    private Map<String, Object> getKafkaConnProps(){
    	Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producerBootstrapAddress);
		props.put("schema.registry.url", schemaRegistryUrl);

		props.put("security.protocol", producerSecurityProtocol);
		props.put("sasl.mechanism", producerSaslMechanism);
		props.put("sasl.jaas.config", producerSaslJaasConfig);
		
//		props.put("basic.auth.credentials.source", srAuthCredentialsSource);
//		props.put("basic.auth.user.info", srUserInfo);
    	return props;
    }
    
    @Bean
    public ProducerFactory<String, AnalysisResult> analysisResultProducerFactory() {
    	Map<String, Object> props = getKafkaConnProps();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
    	return new DefaultKafkaProducerFactory<>(props);
    }
        
    @Bean
    public KafkaTemplate<String, AnalysisResult> analysisResultKafkaTemplate() {
        return new KafkaTemplate<>(analysisResultProducerFactory());
    }
       
}
