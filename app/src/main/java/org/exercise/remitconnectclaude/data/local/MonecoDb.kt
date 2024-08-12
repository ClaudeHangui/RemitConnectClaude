package org.exercise.remitconnectclaude.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import org.exercise.remitconnectclaude.data.Recipient
import org.exercise.remitconnectclaude.data.SupportedMobileWallet
import org.exercise.remitconnectclaude.data.SupportedRecipientCountry
import org.exercise.remitconnectclaude.data.Transaction

@Database(
    entities = [
        SupportedRecipientCountry::class,
        SupportedMobileWallet::class,
        Recipient::class,
        Transaction::class
    ],
    version = 1
)
abstract class MonecoDb : RoomDatabase() {
    abstract fun countriesDao(): SupportedCountriesDao
    abstract fun mobileWalletDao(): MobileWalletDao
    abstract fun recipientDao(): RecipientDao
    abstract fun transactionsDao(): TransactionsDao
}