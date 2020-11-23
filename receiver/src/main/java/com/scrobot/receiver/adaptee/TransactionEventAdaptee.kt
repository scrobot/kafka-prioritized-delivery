package com.scrobot.receiver.adaptee

import com.fasterxml.jackson.databind.ObjectMapper
import com.scrobot.receiver.events.Events
import com.scrobot.receiver.events.Transaction
import com.scrobot.receiver.services.contracts.TransactionContract
import org.springframework.stereotype.Component

@Component(Events.transaction)
class TransactionEventAdaptee(
    override val objectMapper: ObjectMapper,
    private val transactionContract: TransactionContract
): EventAdaptee {

    override fun type(): String = Transaction::class.java.simpleName

    override fun handleEvent(json: String?) {
        json?.let { transactionContract.handle(this.parseJson(it)) }
    }

}