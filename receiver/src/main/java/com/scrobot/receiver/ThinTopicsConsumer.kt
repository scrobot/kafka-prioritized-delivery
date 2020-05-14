package com.scrobot.receiver

import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class ThinTopicsConsumer(
    private val json: Json,
    private val repository: EventRepository
) {

    private val logger = LoggerFactory.getLogger(ThinTopicsConsumer::class.java)

    @KafkaListener(topicPattern = "thin_topic_*", groupId = "thin_topic_consumers")
    fun consume(message: String?) = message?.let {
        json.parse(Event.serializer(), it)
    }?.also {
        repository.save(it)
    } ?: logger.info("empty message was received at ${System.currentTimeMillis()}")

}