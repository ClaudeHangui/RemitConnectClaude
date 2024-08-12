package org.exercise.remitconnectclaude.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhoneContact(
    override var firstName: String,
    override val lastName: String,
    override var phoneNumber: String,
    val profilePictureUrl: String? = null
): BaseUser, Parcelable


interface BaseUser {
    var firstName: String
    val lastName: String
    var phoneNumber: String
}

@Entity(tableName = "Recipient")
data class Recipient(
    @PrimaryKey
    val id: String,
    override var firstName: String,
    override val lastName: String,
    override var phoneNumber: String,
    @ColumnInfo(name = "mobile_wallet")
    val mobileWallet: String,
    val country: String,
    val dialingCode: String
): BaseUser



