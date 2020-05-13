package com.scrobot.deliver

import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import com.scrobot.deliver.ProducerConfig as ThinProducerConfig

class ThinProducerFactory(
    private val config: ThinProducerConfig
) {

    private fun createProducer() = mapOf<String, Any>(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to config.host,
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to config.keySerializer,
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to config.valueSerializer
    ).let {
        DefaultKafkaProducerFactory<String, String>(it)
    }

    fun createTemplate() = KafkaTemplate(createProducer())

}