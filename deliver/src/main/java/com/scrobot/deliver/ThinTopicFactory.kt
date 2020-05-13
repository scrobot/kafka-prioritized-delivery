package com.scrobot.deliver

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.kafka.config.TopicBuilder

class ThinTopicFactory(
    private val topicName: String,
    private val replicas: Int = 1,
    private val partitions: Int = 3
) {

    fun createTopic(): NewTopic = TopicBuilder.name(topicName)
        .partitions(partitions)
        .replicas(replicas)
        .build()

}