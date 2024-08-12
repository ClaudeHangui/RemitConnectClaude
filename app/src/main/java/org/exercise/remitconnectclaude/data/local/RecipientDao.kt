package org.exercise.remitconnectclaude.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.exercise.remitconnectclaude.data.Recipient

@Dao
interface RecipientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPreviousRecipients(items: List<Recipient>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createRecipient(recipient: Recipient) : Long

    @Query("select * from Recipient")
    suspend fun getPreviousRecipients(): List<Recipient>

}