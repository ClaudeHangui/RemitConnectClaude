package org.exercise.remitconnectclaude.presentation.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.exercise.remitconnectclaude.data.Recipient
import org.exercise.remitconnectclaude.data.RecipientRepository
import org.exercise.remitconnectclaude.data.TransactionsRepository
import org.exercise.remitconnectclaude.util.SharedPrefHelper
import org.exercise.remitconnectclaude.util.SharedPrefHelper.Companion.PREF_RECIPIENT_ID
import org.exercise.remitconnectclaude.util.SharedPrefHelper.Companion.PREF_USER_BALANCE
import java.text.NumberFormat
import java.text.ParseException
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class SendMoneyToAfricaViewModel @Inject constructor(
    private val recipientRepository: RecipientRepository,
    private val transactionsRepository: TransactionsRepository,
    private val sharedPrefHelper: SharedPrefHelper
) : ViewModel() {

    private val _formState = MutableStateFlow(TransactionState())
    val formState = _formState.asStateFlow()


    fun updatePhoneDialingCode(dialingCode: String) {
        _formState.update {
            it.copy(input = it.input.copy(countryDialingCode = dialingCode))
        }
    }

    fun updateFirstName(firstName: String) {
        _formState.update {
            it.copy(input = it.input.copy(firstName = firstName))
        }
    }

    fun updateLastName(lastName: String) {
        _formState.update {
            it.copy(input = it.input.copy(lastName = lastName))
        }
    }

    fun updatePhoneNumber(phoneNumber: String) {
        val formatPhone = formatPhoneNumber(phoneNumber)
        _formState.update {
            it.copy(input = it.input.copy(phoneNumber = formatPhone))
        }
    }

    fun saveProviderSelected(id: String, name: String) = viewModelScope.launch {
        _formState.update {
            it.copy(
                input = it.input.copy(
                    mobileMoneyProviderId = id,
                    mobileMoneyProviderName = name
                )
            )
        }
    }

    fun updateAmountProvided(amount: String) {
        _formState.update {
            it.copy(input = it.input.copy(amountReceived = amount))
        }
    }

    fun updateCountrySelected(name: String) {
        _formState.update {
            it.copy(input = it.input.copy(countryName = name))
        }
    }

    private fun formatPhoneNumber(phoneNumber: String): String {
        return if (phoneNumber.isEmpty()) "" else {
            val formattedNumber = StringBuilder()
            formattedNumber.append(phoneNumber.substring(0, 2))
            formattedNumber.append(" ")
            formattedNumber.append(phoneNumber.substring(2, 5))
            formattedNumber.append(" ")
            formattedNumber.append(phoneNumber.substring(5))

            formattedNumber.toString()
        }
    }

    fun simulateTransaction(firstName: String, lastName: String, amountSentInEuros: String) =
        viewModelScope.launch {
            _formState.update {
                it.copy(loading = true)
            }
            val result = withContext(Dispatchers.IO) {
                val parseAmount = parseNumberWithLocale(amountSentInEuros)
                val previousRecipientId = sharedPrefHelper.getData(PREF_RECIPIENT_ID, "")
                val newRecipientId = previousRecipientId.toInt().plus(1)
                val newRecipient = Recipient(
                    newRecipientId.toString(),
                    firstName = firstName,
                    lastName = lastName,
                    phoneNumber = _formState.value.input.phoneNumber,
                    country = _formState.value.input.countryName,
                    dialingCode = _formState.value.input.countryDialingCode,
                    mobileWallet = _formState.value.input.mobileMoneyProviderName
                )
                val res = recipientRepository.insertRecipient(newRecipient)
                Log.e("saving recipient", " result $res")

                transactionsRepository.saveTransaction(
                    firstName,
                    lastName,
                    parseAmount?.toDouble() ?: 0.0
                )
            }

            delay(3000L)

            if (result > 0) {
                updateUserBalance(amountSentInEuros)
            }

            _formState.update {
                it.copy(loading = false, isTransactionSuccessful = result > 0)
            }
        }

    private fun parseNumberWithLocale(input: String): Number? {
        val numberFormat = NumberFormat.getInstance(Locale.FRANCE)
        return try {
            numberFormat.parse(input)
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
    }

    private fun updateUserBalance(amount: String) {
        val currentBalance = sharedPrefHelper.getData(PREF_USER_BALANCE, "")
        val parseBalance = parseNumberWithLocale(currentBalance)
        val parsedAmount = parseNumberWithLocale(amount)
        if (parsedAmount != null && parseBalance != null) {
            val newBalance = parseBalance.toDouble().minus(parsedAmount.toDouble())
            sharedPrefHelper.saveData(PREF_USER_BALANCE, newBalance.toString())
        }
    }

    fun resetState() {
        _formState.value = TransactionState()
    }
}

data class TransactionState(
    var loading: Boolean = false,
    var input: Form = Form(),
    var isTransactionSuccessful: Boolean = false
)

data class Form(
    var firstName: String = "",
    var lastName: String = "",
    var phoneNumber: String = "",
    var mobileMoneyProviderName: String = "",
    var mobileMoneyProviderId: String = "",
    var amountReceived: String = "",
    var countryDialingCode: String = "",
    var countryName: String = ""
)