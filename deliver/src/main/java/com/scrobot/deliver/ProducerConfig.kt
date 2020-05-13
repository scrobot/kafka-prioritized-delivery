package com.scrobot.deliver

data class ProducerConfig(
    val host: String,
    val keySerializer: Any,
    val valueSerializer: Any
)