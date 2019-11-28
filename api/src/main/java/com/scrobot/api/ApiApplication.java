package com.scrobot.api;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Bean
	public KafkaAdmin admin() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		return new KafkaAdmin(configs);
	}

	@Bean
	public NewTopic topic() {
		return TopicBuilder.name("messages_2")
			.partitions(1)
			.replicas(1)
			.config(TopicConfig.COMPRESSION_TYPE_CONFIG, "zstd")
			.build();
	}

}
