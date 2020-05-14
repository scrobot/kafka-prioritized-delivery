package com.scrobot.receiver.services

import com.scrobot.receiver.events.Transaction
import com.scrobot.receiver.helpers.LoggerTrait
import com.scrobot.receiver.services.contracts.TransactionContract
import org.springframework.stereotype.Service

@Service
class TransactionService: TransactionContract, LoggerTrait {

    override fun handle(transaction: Transaction) {
        logger().info(transaction.toString())
    }

}