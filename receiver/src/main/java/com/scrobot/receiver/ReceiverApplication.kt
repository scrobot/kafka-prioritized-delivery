package com.scrobot.receiver;

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
open class ReceiverApplication {
    @Bean
    open fun json(): Json = Json(JsonConfiguration.Stable)
}

fun main(args: Array<String>) {
    runApplication<ReceiverApplication>(*args)
}