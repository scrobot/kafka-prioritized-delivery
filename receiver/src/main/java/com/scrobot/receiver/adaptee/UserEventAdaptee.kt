package com.scrobot.receiver.adaptee

import com.fasterxml.jackson.databind.ObjectMapper
import com.scrobot.receiver.events.Events
import com.scrobot.receiver.events.UserEvent
import com.scrobot.receiver.events.UserEventType.*
import com.scrobot.receiver.services.UserService
import org.springframework.stereotype.Component

@Component(Events.user)
class UserEventAdaptee(
    override val objectMapper: ObjectMapper,
    private val userService: UserService
): EventAdaptee {

    override fun type(): String = Events.user

    override fun handleEvent(json: String?) {
        json?.let {
                when(this.parseJson<UserEvent>(it).type) {
                    CREATE -> this.parseJson<UserEvent.CreateUserEvent>(it)
                    UPDATE -> this.parseJson<UserEvent.UpdateUserEvent>(it)
                    DELETE -> this.parseJson<UserEvent.DeleteUserEvent>(it)
                }
            }
            ?.also(this::handleUserAction)
    }

    private fun handleUserAction(event: UserEvent) {
        with(userService) {
            when(event) {
                is UserEvent.CreateUserEvent -> createUser(event)
                is UserEvent.UpdateUserEvent -> updateUser(event)
                is UserEvent.DeleteUserEvent -> deleteUser(event)
            }
        }
    }

}