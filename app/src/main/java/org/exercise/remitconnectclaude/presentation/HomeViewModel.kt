package org.exercise.remitconnectclaude.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.exercise.remitconnectclaude.data.TransactionsRepository
import org.exercise.remitconnectclaude.presentation.ui.utils.ResourceHelper
import org.exercise.remitconnectclaude.presentation.ui.homeNavigation.HomeScreenState
import org.exercise.remitconnectclaude.util.EURO
import org.exercise.remitconnectclaude.util.SharedPrefHelper
import org.exercise.remitconnectclaude.util.SharedPrefHelper.Companion.PREF_USER_BALANCE
import org.exercise.remitconnectclaude.util.SharedPrefHelper.Companion.PREF_USER_NAME
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
    private val resourceHelper: ResourceHelper,
    private val sharedPrefHelper: SharedPrefHelper
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeScreenState())
    val homeState = _homeState.asStateFlow()

    init {
        getUserBalance()
        getLatestTransactions()
    }

    private fun getUserBalance() = viewModelScope.launch {
        val currentUserBalance = sharedPrefHelper.getData(PREF_USER_BALANCE, "").ifEmpty {
            resourceHelper.generateRandomSixDigitEndingWithZero()
        }
        sharedPrefHelper.saveData(PREF_USER_BALANCE, currentUserBalance)

        val currentUserName = sharedPrefHelper.getData(PREF_USER_NAME, "").ifEmpty {
            resourceHelper.getRandomUsername()
        }
        sharedPrefHelper.saveData(PREF_USER_NAME, currentUserName)


        _homeState.update {
            it.copy(
                userName = currentUserName,
                userBalance = currentUserBalance,
                userCurrency = EURO,
                internalOperations = resourceHelper.buildSupportedInternalOperations()
            )
        }
    }

    private fun getLatestTransactions() = viewModelScope.launch(Dispatchers.IO) {
        transactionsRepository.getAllTransactions().collectLatest { transactions ->
            withContext(Dispatchers.Main){
                _homeState.update {
                    it.copy(latestTransactions = transactions)
                }
            }
        }
    }
}