package org.exercise.remitconnectclaude.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.exercise.remitconnectclaude.data.Transaction


@Dao
interface TransactionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTransaction(transactionItem: Transaction): Long

    @Query("select * from `Transaction`")
    fun getPreviousTransactions(): Flow<List<Transaction>>
}