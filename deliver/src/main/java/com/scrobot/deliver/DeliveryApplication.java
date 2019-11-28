package com.scrobot.deliver;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.*;

@SpringBootApplication
public class DeliveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryApplication.class, args);
	}

	@Bean
	public KafkaAdmin admin() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		return new KafkaAdmin(configs);
	}

	@Bean
	public NewTopic lowDeliverPriorityTopic() {
		return TopicBuilder.name("low_deliver_priority")
			.partitions(3)
			.replicas(3)
			.config(TopicConfig.COMPRESSION_TYPE_CONFIG, "zstd")
			.build();
	}

	@Bean
	public NewTopic MediumDeliverPriorityTopic() {
		return TopicBuilder.name("medium_deliver_priority")
			.partitions(3)
			.replicas(3)
			.config(TopicConfig.COMPRESSION_TYPE_CONFIG, "zstd")
			.build();
	}

	@Bean
	public NewTopic HighDeliverPriorityTopic() {
		return TopicBuilder.name("high_deliver_priority")
			.partitions(3)
			.replicas(3)
			.config(TopicConfig.COMPRESSION_TYPE_CONFIG, "zstd")
			.build();
	}

}
