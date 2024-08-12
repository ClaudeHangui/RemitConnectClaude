package org.exercise.remitconnectclaude.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "SupportedRecipientCountry")
@Parcelize
data class SupportedRecipientCountry(
    @PrimaryKey
    val id: String,
    val currency_code: String,
    val name: String,
    var international_dialing_code: String,
    var flag_url: String
) : Parcelable