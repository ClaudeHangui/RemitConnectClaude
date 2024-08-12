package org.exercise.remitconnectclaude.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SupportedMobileWallet")
data class SupportedMobileWallet(
    @PrimaryKey
    val id: String,
    val name: String
)