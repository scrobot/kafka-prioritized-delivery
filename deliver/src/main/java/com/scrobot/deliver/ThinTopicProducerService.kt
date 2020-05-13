package com.scrobot.deliver

import kotlinx.serialization.json.Json
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class ThinTopicProducerService(
    @Value("\${spring.kafka.producer.bootstrap-servers}")
    private val host: String,
    @Value("\${spring.kafka.producer.key-serializer}")
    private val keySerializer: String,
    @Value("\${spring.kafka.producer.value-serializer}")
    private val valueSerializer: String,
    private val json: Json
) {

    private val topicPrefix = "thin_topic_"
    private val producersCache = mutableMapOf<Int, KafkaTemplate<String, String>>()

    fun sendMessageToTopic(topicNumber: Int) = Pair(
        EventGenerator.generateNextEvent(),
        getProducerByTopicNumber(topicNumber)
    ).also { (event, producer) ->
        producer.send(
            ProducerRecord("$topicPrefix$topicNumber", event.type, json.stringify(Event.serializer(), event))
        )
    }

    private fun getProducerByTopicNumber(topicNumber: Int): KafkaTemplate<String, String> =
        producersCache[topicNumber] ?: ThinProducerFactory(
            ProducerConfig(host, Class.forName(keySerializer), Class.forName(valueSerializer))
        ).createTemplate().apply {
            ThinTopicFactory("$topicPrefix$topicNumber").createTopic()
            producersCache[topicNumber] = this
        }

}