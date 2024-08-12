package org.exercise.remitconnectclaude.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Transaction")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transaction_id")
    val transactionId: Int = 0,
    @ColumnInfo("recipient_name")
    val recipientName: String,
    @ColumnInfo(name = "amount_sent")
    val amountSent: Double
)
