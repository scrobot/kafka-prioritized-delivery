package com.scrobot.receiver.services

import com.scrobot.receiver.events.Metric
import com.scrobot.receiver.helpers.LoggerTrait
import com.scrobot.receiver.services.contracts.MetricContract
import org.springframework.stereotype.Service

@Service
class MetricService: MetricContract, LoggerTrait {

    override fun acknowledge(metric: Metric) {
        logger().info(metric.toString())
    }

}