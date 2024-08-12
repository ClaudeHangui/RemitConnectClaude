package org.exercise.remitconnectclaude.presentation.ui.inputAmount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.exercise.remitconnectclaude.presentation.ui.utils.ResourceHelper
import org.exercise.remitconnectclaude.util.SharedPrefHelper
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.text.ParseException
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ValidateInputAmountViewModel @Inject constructor(
    private val resourceHelper: ResourceHelper,
    private val sharedPrefHelper: SharedPrefHelper
) : ViewModel() {

    private val _amountToSpendState = MutableStateFlow(AmountToSpendState())
    val amountToSpendState = _amountToSpendState.asStateFlow()

    init {
        getUserBalance()
    }

    companion object {
        const val ONE_EURO = 655.94
    }

    private fun getUserBalance() = viewModelScope.launch {
        val balance = sharedPrefHelper.getData(SharedPrefHelper.PREF_USER_BALANCE, "")
        val currentUserBalance = balance.ifEmpty {
            resourceHelper.generateRandomSixDigitEndingWithZero()
        }

        _amountToSpendState.update {
            it.copy(
                currentBalance = currentUserBalance,
            )
        }
    }

    private fun convertAmountToRecipientCurrency(amount: Double) = amount * ONE_EURO

    private fun calculateMonecoFees(amount: Double) = amount * 0.01


    fun calculateAmountToSpend(inputAmount: String, balance: String) = viewModelScope.launch {
        if (inputAmount.isEmpty() || inputAmount == 0.toString()) {
            _amountToSpendState.update {
                it.copy(
                    inputCardColorState = InputCardColorState.Default
                )
            }
        } else {
            val number = parseNumberWithLocale(inputAmount)
            val parsedBalance = parseNumberWithLocale(balance)

            if (number != null && parsedBalance != null) {
                val numConverted = BigDecimal(number.toDouble()).setScale(3, RoundingMode.HALF_UP)
                val balanceConverted = BigDecimal(parsedBalance.toDouble()).setScale(3, RoundingMode.HALF_UP)

                if (numConverted.toDouble() < 0.0f || ((balanceConverted.toDouble() > 0) && (numConverted.toDouble() - balanceConverted.toDouble() > 0))) {
                    _amountToSpendState.update {
                        it.copy(
                            inputCardColorState = InputCardColorState.Warning
                        )
                    }
                } else {
                    val totalReceived = convertAmountToRecipientCurrency(numConverted.toDouble())
                    val monecoFees = calculateMonecoFees(numConverted.toDouble())
                    val totalSpent = numConverted.toDouble() + monecoFees

                    val totalConverted =
                        BigDecimal(totalSpent).setScale(3, RoundingMode.HALF_UP)
                    val totalReceivedConverted =
                        BigDecimal(totalReceived).setScale(3, RoundingMode.HALF_UP)


                    _amountToSpendState.update {
                        it.copy(
                            inputCardColorState = InputCardColorState.Valid,
                            amount = Amount(
                                totalConverted.toString(),
                                totalReceivedConverted.toString(),
                                monecoFees.toString()
                            ),
                        )
                    }
                }
            }
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
}

data class AmountToSpendState(
    val currentBalance: String = "",
    val amount: Amount = Amount("", "", ""),
    val inputCardColorState: InputCardColorState = InputCardColorState.Default,
)

sealed class InputCardColorState {
    object Default : InputCardColorState()
    object Warning : InputCardColorState()
    object Valid : InputCardColorState()
}

data class Amount(
    val amountSpent: String,
    val amountReceived: String,
    val monecoFees: String,
)