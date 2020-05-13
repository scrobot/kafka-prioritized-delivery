package com.scrobot.deliver

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.util.*

@Service
class FatTopicProducerService(
    private val json: Json,
    private val kafkaTemplate: KafkaTemplate<String, String>
) {

    private val logger = LoggerFactory.getLogger(FatTopicProducerService::class.java)

    fun sendMessage() = EventGenerator
        .generateNextEvent()
        .also {event ->
            kafkaTemplate.send(ProducerRecord(TOPIC, event.type, json.stringify(Event.serializer(), event)))
        }

    companion object {
        private const val TOPIC = "fat_topic"
    }
}

