package org.exercise.remitconnectclaude.data

import org.exercise.remitconnectclaude.data.local.TransactionsDao
import javax.inject.Inject

class TransactionsRepository @Inject constructor(
    private val monecoDao: TransactionsDao
) {

    fun getAllTransactions() = monecoDao.getPreviousTransactions()

    suspend fun saveTransaction(firstName: String, lastName: String, amount: Double) = monecoDao.saveTransaction(
        Transaction(
            recipientName = "$firstName $lastName",
            amountSent = amount
        )
    )
}