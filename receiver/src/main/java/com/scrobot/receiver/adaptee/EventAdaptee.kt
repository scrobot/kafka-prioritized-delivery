package com.scrobot.receiver.adaptee

import com.fasterxml.jackson.databind.ObjectMapper

interface EventAdaptee {

    val objectMapper: ObjectMapper

    fun type(): String

    fun handleEvent(json: String?)
}

inline fun <reified T> EventAdaptee.parseJson(json: String): T = objectMapper.readValue(json, T::class.java)