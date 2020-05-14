package com.scrobot.receiver.services

import com.scrobot.receiver.events.UserEvent
import com.scrobot.receiver.helpers.LoggerTrait
import com.scrobot.receiver.services.contracts.UserContract
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserService : UserContract, LoggerTrait {

    override fun createUser(dto: UserEvent.CreateUserEvent) {
        logger().info("create user event -> $dto")
    }

    override fun updateUser(dto: UserEvent.UpdateUserEvent) {
        logger().info("update user event -> $dto")
    }

    override fun deleteUser(dto: UserEvent.DeleteUserEvent) {
        logger().info("delete user event -> $dto")
    }
}