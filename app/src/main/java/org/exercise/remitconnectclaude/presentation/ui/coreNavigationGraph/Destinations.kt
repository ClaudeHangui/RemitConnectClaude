import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destinations(
    val route: String,
    val title: String? = null,
    val icon: ImageVector? = null,
) {
    object TransactionScreen : Destinations(
        route = "transaction_screen",
        title = "Home",
        icon = Icons.Default.Home
    )

    object CardsScreen : Destinations(
        route = "cards_screen",
        title = "Cards",
        icon = Icons.Default.Call
    )

    object SendMoneyScreen : Destinations(
        route = "send_money_screen",
        title = "Send",
        icon = Icons.Default.Star
    )

    object TontinesScreen : Destinations(
        route = "tontines_screen",
        title = "Tontines",
        icon = Icons.Default.Refresh
    )

    object SettingsScreen : Destinations(
        route = "settings_screen",
        title = "Settings",
        icon = Icons.Default.Settings
    )

    fun withArgsFormat(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/{$arg}")
            }
        }
    }

    object HomeScreen : Destinations("home_screen")
    object SendToAfricaScreen : Destinations("send_to_africa_screen"){
        val itemId = "itemId"
        val itemName = "itemName"
    }
    object RecipientDetailsScreen: Destinations("recipient_details_screen")
    object SelectMobileWalletScreen: Destinations("select_mobile_wallet_screen")
    object InputAmountToSendScreen: Destinations("input_amount_to_screen")
    object SuccessfulTransactionScreen: Destinations("successful_transaction_screen")
}
