package com.scrobot.kafkadelivery;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactoryUtils;

import javax.annotation.PostConstruct;
import java.util.*;

@SpringBootApplication
public class KafkadeliveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkadeliveryApplication.class, args);
	}

	@Bean
	public KafkaAdmin admin() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		return new KafkaAdmin(configs);
	}

	@Bean
	public NewTopic topic() {
		return TopicBuilder.name("test")
			.assignReplicas(0, Arrays.asList(0, 1))
			.assignReplicas(1, Arrays.asList(1, 2))
			.assignReplicas(2, Arrays.asList(2, 0))
			.config(TopicConfig.COMPRESSION_TYPE_CONFIG, "zstd")
			.build();
	}

}
