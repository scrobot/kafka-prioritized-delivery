package com.scrobot.receiver

import kotlinx.serialization.Serializable
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
@Serializable
data class Event(
    @Id
    val id: String,
    val payload: String?,
    val type: String
)