package com.scrobot.deliver

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.apache.kafka.clients.admin.AdminClientConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.kafka.core.KafkaAdmin
import reactor.core.publisher.Flux
import java.util.*
import javax.annotation.PostConstruct
import kotlin.random.Random
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds
import kotlin.time.toJavaDuration


fun main(args: Array<String>) {
    runApplication<DeliveryApplication>(*args)
}

@SpringBootApplication
open class DeliveryApplication {

    @Autowired
    private val fatTopicProducerService: FatTopicProducerService? = null

    @Autowired
    private val thinTopicProducerService: ThinTopicProducerService? = null

    @Value("\${kafka.topic-sender-type}")
    private lateinit var type: String

    @Bean
    open fun admin(): KafkaAdmin? {
        val configs: MutableMap<String, Any> =
            HashMap()
        configs[AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
        return KafkaAdmin(configs)
    }

    @Bean
    open fun json(): Json = Json(JsonConfiguration.Stable)

    @ExperimentalTime
    @PostConstruct
    fun generateMessages() {
        // Генерируем небольшую нагрузку на "толстый" топик, чтобы проверить как происходит ребаланс консюмеров
        Flux.interval(1.milliseconds.toJavaDuration())
            .subscribe {
                fatTopicProducerService?.sendMessage()
            }
    }


    /**
     * Если нужно поэкспериментировать с высокой нагрузкой на топики, и какая модель лучше работает, то запустите данный метод
     * */
    @ExperimentalTime
    private fun generateHighLoadIntoKafka() {
        when (type) {
            "Fat" -> for (i in 0..1000) {
                Flux.interval(1.milliseconds.toJavaDuration())
                    .subscribe {
                        // Генерируем нагрузку на "толстый" топик
                        fatTopicProducerService?.sendMessage()
                    }
            }
            else -> for (i in 0..1000) {
                Flux.interval(1.milliseconds.toJavaDuration())
                    .map { i }
                    .subscribe {
                        // Генерируем нагрузку на динамичное создание "тонких" топиков
                        thinTopicProducerService?.sendMessageToTopic(it)
                    }
            }
        }
    }

}