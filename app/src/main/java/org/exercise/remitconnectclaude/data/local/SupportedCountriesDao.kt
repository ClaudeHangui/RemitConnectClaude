package org.exercise.remitconnectclaude.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.exercise.remitconnectclaude.data.SupportedRecipientCountry

@Dao
interface SupportedCountriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRecipientCountries(countries: List<SupportedRecipientCountry>)

    @Query("select * from SupportedRecipientCountry")
    suspend fun getAllSupportedCountries(): List<SupportedRecipientCountry>
}