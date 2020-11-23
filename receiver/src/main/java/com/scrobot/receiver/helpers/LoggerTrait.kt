package com.scrobot.receiver.helpers

import org.slf4j.LoggerFactory

interface LoggerTrait {

    fun logger() = LoggerFactory.getLogger(this::class.java)

}