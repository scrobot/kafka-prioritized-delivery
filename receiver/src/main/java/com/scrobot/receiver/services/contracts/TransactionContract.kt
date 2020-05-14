package com.scrobot.receiver.services.contracts

import com.scrobot.receiver.events.Transaction

interface TransactionContract {

    fun handle(transaction: Transaction)

}