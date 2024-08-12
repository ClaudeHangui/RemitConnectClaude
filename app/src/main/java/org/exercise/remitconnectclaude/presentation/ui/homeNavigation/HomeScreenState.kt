package org.exercise.remitconnectclaude.presentation.ui.homeNavigation

import org.exercise.remitconnectclaude.data.Transaction
import org.exercise.remitconnectclaude.presentation.ui.utils.SupportedInternalOperationItem

data class HomeScreenState(
    val userName: String = "",
    val userBalance: String = "",
    val userCurrency: String = "",
    val internalOperations: List<SupportedInternalOperationItem> = emptyList(),
    val latestTransactions: List<Transaction> = emptyList(),
    val loading: Boolean = false
)
