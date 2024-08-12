package org.exercise.remitconnectclaude.presentation.ui.selectWallet

import androidx.annotation.DrawableRes

data class MobileMoneyProvider (
    val providerId: String,
    val providerName: String,
    @DrawableRes val providerLogo: Int
)