package com.scrobot.receiver.events

import java.time.OffsetDateTime

data class Transaction(
    val id: String,
    val status: Status = Status.UNKNOWN,
    val createdAt: OffsetDateTime? = null
) {
    enum class Status {
        UNKNOWN, ERROR, OK
    }
}

