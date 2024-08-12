package org.exercise.remitconnectclaude.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.exercise.remitconnectclaude.data.SupportedMobileWallet

@Dao
interface MobileWalletDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRecipientSupportedWallets(mobileWallets: List<SupportedMobileWallet>)

    @Query("select * from SupportedMobileWallet")
    suspend fun getAllWallets(): List<SupportedMobileWallet>
}