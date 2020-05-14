package com.scrobot.receiver.services.contracts

import com.scrobot.receiver.events.Metric

interface MetricContract {

    fun acknowledge(metric: Metric)

}