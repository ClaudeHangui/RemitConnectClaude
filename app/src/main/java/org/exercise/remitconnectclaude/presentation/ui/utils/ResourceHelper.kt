package org.exercise.remitconnectclaude.presentation.ui.utils

import android.content.Context
import androidx.annotation.DrawableRes
import org.exercise.remitconnectclaude.R
import java.text.DecimalFormat
import javax.inject.Inject
import kotlin.random.Random

class ResourceHelper @Inject constructor(private val applicationContext: Context) {

    fun buildSupportedInternalOperations() = listOf(
        SupportedInternalOperationItem(
            1,
            applicationContext.resources.getString(R.string.top_up_balance),
            R.drawable.ic_moneco_top_up_balance
        ),
        SupportedInternalOperationItem(
            2,
            applicationContext.resources.getString(R.string.withdraw_money),
            R.drawable.ic_moneco_withdraw_money
        ),
        SupportedInternalOperationItem(
            3,
            applicationContext.resources.getString(R.string.get_iban), R.drawable.ic_moneco_get_iban
        ),
        SupportedInternalOperationItem(
            4,
            applicationContext.resources.getString(R.string.view_analytics),
            R.drawable.ic_moneco_view_analytics
        ),
    )

    fun buildSendMoneyTypes() = listOf(
        SendMoneyType(
            1,
            applicationContext.resources.getString(R.string.sent_money_type_moneco_balance),
            R.drawable.ic_send_money_to_moneco_balance
        ),
        SendMoneyType(
            2,
            applicationContext.resources.getString(R.string.sent_money_type_bank_transfer),
            R.drawable.ic_send_money_via_bank_transfer
        ),
        SendMoneyType(
            3,
            applicationContext.resources.getString(R.string.sent_money_type_send_to_africa),
            R.drawable.ic_send_money_to_africa
        ),
    )

    fun methodsToSendToAfrica() = listOf(
        SendMoneyType(
            10,
            applicationContext.resources.getString(R.string.mobile_wallets),
            R.drawable.ic_arrow_green_square_round_corner
        ),
        SendMoneyType(
            11,
            applicationContext.resources.getString(R.string.sent_money_type_bank_transfer),
            R.drawable.ic_arrow_green_square_round_corner
        ),
    )

    fun generateRandomSixDigitEndingWithZero(): String {
        val fourDigitNumber = Random.nextInt(10_00, 10000)
        val sixDigitNumber = fourDigitNumber * 100
        val decimalFormat = DecimalFormat("#,###")
        return try {
            decimalFormat.format(sixDigitNumber)
        } catch (e: Exception) {
            e.printStackTrace()
            "0"
        }
    }


    fun getRandomUsername(): String {
        // List of 10 usernames
        val usernames = listOf(
            "Alice Smith",
            "Bob Johnson",
            "Charlie Brown",
            "Diana Prince",
            "Evan Miller",
            "Fiona Green",
            "George Martin",
            "Hannah Lee",
            "Isaac Walker",
            "Judy Adams"
        )

        // Randomly select a username from the list
        val randomIndex = Random.nextInt(usernames.size)
        return usernames[randomIndex]
    }

    fun getLogo(name: String) = when {
        name.contains("Cash") -> {
            R.drawable.cash_plus_img
        }

        name.contains("Orange") -> {
            R.drawable.orange_money
        }

        name.contains("Wave") -> {
            R.drawable.wave_mobile_money
        }

        else -> {
            R.drawable.moov_logo
        }
    }

}

data class SupportedInternalOperationItem(
    val operationId: Int,
    val operationName: String,
    @DrawableRes val operationIcon: Int
)

data class SendMoneyType(
    val typeId: Int,
    val typeName: String,
    @DrawableRes val typeIcon: Int
)