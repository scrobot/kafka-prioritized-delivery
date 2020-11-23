package com.scrobot.receiver

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class FatTopicConsumer(
    private val adapter: EventAdapter,
    private val repository: EventRepository
) {

    private val logger: Logger = LoggerFactory.getLogger(FatTopicConsumer::class.java)

    private val json = Json(JsonConfiguration.Stable)

    @KafkaListener(topics = ["fat_topic"], groupId = "fat_topic_consumers")
    fun persistEvent(message: String?) = message?.let {
        json.parse(Event.serializer(), it)
    }?.also {
        repository.save(it)
    } ?: logger.info("empty message was received at ${System.currentTimeMillis()}")

    @KafkaListener(topics = ["fat_topic"], groupId = "fat_topic_consumers")
    fun consume(message: String?) = message?.let {
        json.parse(Event.serializer(), it)
    }?.also {
        adapter.handleEvent(it)
    } ?: logger.info("empty message was received at ${System.currentTimeMillis()}")

}