package org.exercise.remitconnectclaude.data.remote

data class RecipientListResponseItem(
    val id: String,
    val country: String,
    val mobile_wallet: String,
    val name: String
)