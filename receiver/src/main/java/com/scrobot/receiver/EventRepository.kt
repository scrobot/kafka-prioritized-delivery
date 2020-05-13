package com.scrobot.receiver

import org.springframework.data.mongodb.repository.MongoRepository

interface EventRepository: MongoRepository<Event, String> {
}