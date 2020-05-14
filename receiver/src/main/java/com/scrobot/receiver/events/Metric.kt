package com.scrobot.receiver.events

data class Metric(
    val key: String,
    val value: String,
    val metadata: String? = null
)