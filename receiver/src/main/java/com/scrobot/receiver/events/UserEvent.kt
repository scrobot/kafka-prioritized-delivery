package com.scrobot.receiver.events

import com.scrobot.receiver.events.UserEventType.*

sealed class UserEvent(
    val type: UserEventType
) {

    data class CreateUserEvent(val params: Map<String, String>): UserEvent(CREATE)
    data class UpdateUserEvent(val id: String, val params: Map<String, String>): UserEvent(UPDATE)
    data class DeleteUserEvent(val id: String): UserEvent(DELETE)

}

enum class UserEventType {
    CREATE, UPDATE, DELETE
}