package com.scrobot.receiver.adaptee

import com.fasterxml.jackson.databind.ObjectMapper
import com.scrobot.receiver.events.Events
import com.scrobot.receiver.events.Metric
import com.scrobot.receiver.services.contracts.MetricContract
import org.springframework.stereotype.Component

@Component(Events.metric)
class MetricEventAdaptee(
    override val objectMapper: ObjectMapper,
    private val metricContract: MetricContract
): EventAdaptee {

    override fun type(): String = Metric::class.java.simpleName

    override fun handleEvent(json: String?) {
        json?.let { metricContract.acknowledge(this.parseJson(it)) }
    }

}