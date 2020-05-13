package com.scrobot.deliver

import java.util.*

object EventGenerator {

    fun generateNextEvent(): Event = Pair(Random().nextInt(), UUID.randomUUID().toString())
        .let { (random, uuid) ->
            when(random % 3) {
                0 -> Event(uuid, null, type = "view")
                1 -> Event(uuid, "{}", type = "write")
                2 -> Event(uuid, "{\"id\": ${random}}", type = "delete")
                else -> Event(uuid, null, type = "undefined")
            }
        }

}