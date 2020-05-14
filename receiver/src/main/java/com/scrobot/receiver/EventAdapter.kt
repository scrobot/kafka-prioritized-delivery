package com.scrobot.receiver

import com.scrobot.receiver.adaptee.EventAdaptee
import org.springframework.beans.factory.BeanFactory
import org.springframework.stereotype.Component

@Component
class EventAdapter(
    private val beanFactory: BeanFactory
) {

    fun handleEvent(event: Event) {
        beanFactory.getBean(event.type, EventAdaptee::class.java)
            .handleEvent(event.payload)
    }

}