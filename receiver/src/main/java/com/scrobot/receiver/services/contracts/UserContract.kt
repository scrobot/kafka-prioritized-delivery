package com.scrobot.receiver.services.contracts

import com.scrobot.receiver.events.UserEvent
import com.scrobot.receiver.events.UserEvent.*

interface UserContract {

    fun createUser(dto: CreateUserEvent)
    fun updateUser(dto: UpdateUserEvent)
    fun deleteUser(dto: DeleteUserEvent)

}