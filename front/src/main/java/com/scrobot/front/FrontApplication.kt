package com.scrobot.front

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping

@SpringBootApplication
open class FrontApplication {

    @GetMapping("/health")
    fun healthCheck() = "200 OK"

}

fun main(args: Array<String>) {
    runApplication<FrontApplication>(*args)
}