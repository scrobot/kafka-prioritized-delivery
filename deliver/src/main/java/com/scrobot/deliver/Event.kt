package com.scrobot.deliver

import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val id: String,
    val payload: String?,
    val type: String
)