package org.exercise.remitconnectclaude.presentation.ui.inputAmount

import androidx.annotation.ColorRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.exercise.remitconnectclaude.R
import org.exercise.remitconnectclaude.presentation.theme.outfitFamily
import org.exercise.remitconnectclaude.util.EURO

@Composable
fun InputAmountToSendTopSectionScreen(
    modifier: Modifier = Modifier,
    onBackButtonClicked: () -> Unit,
    isSelected: Boolean,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        val borderColor = if (isSelected) colorResource(id = R.color.label_text_green)
        else colorResource(id = R.color.notification_bgd)


        IconButton(
            onClick = {
                onBackButtonClicked.invoke()
            },
            modifier = Modifier
                .padding(top = 64.dp, start = 24.dp, end = 24.dp)
                .width(32.dp)
                .height(32.dp)
                .background(
                    color = borderColor,
                    shape = RoundedCornerShape(10.dp)
                )
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                tint = colorResource(id = R.color.dark_blue),
                contentDescription = null,
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
            )
        }

        Text(
            text = stringResource(id = R.string.send_money),
            color = colorResource(id = R.color.dark_blue),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(top = 24.dp, start = 24.dp)
                .wrapContentWidth()
                .wrapContentHeight()
        )

        Text(
            text = stringResource(id = R.string.how_much),
            color = colorResource(id = R.color.dark_blue),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(top = 36.dp, start = 24.dp)
                .wrapContentWidth()
                .wrapContentHeight()
        )
    }
}

@Composable
fun setUserBalance(userBalance: String, currency: String, inputAmountColor: InputAmountColor) =
    buildAnnotatedString {
        if (inputAmountColor.borderColor == R.color.rounded_border_gray) {
            inputAmountColor.borderColor = R.color.dark_blue
        }


        withStyle(
            style = SpanStyle(
                color = colorResource(inputAmountColor.borderColor),
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = outfitFamily
            )
        ) {
            append(stringResource(id = R.string.your_balance_is))
        }
        append(" ")
        withStyle(
            style = SpanStyle(
                color = colorResource(inputAmountColor.borderColor),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = outfitFamily
            )
        ) {
            append("$userBalance $currency ")
        }
    }

data class InputAmountColor(
    @ColorRes var borderColor: Int = R.color.rounded_border_gray,
    @ColorRes var balanceContainerColor: Int = R.color.notification_bgd
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BalanceCard(
    balance: String,
    inputCardColorState: InputCardColorState, onInputChange: (String) -> Unit
) {
    var inputAmount by remember { mutableStateOf(TextFieldValue("")) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val currentBalance = remember {
        balance
    }

    val inputAmountContainerColor = remember(inputCardColorState) {
        when (inputCardColorState) {
            is InputCardColorState.Default -> InputAmountColor().apply {
                borderColor = R.color.rounded_border_gray
                balanceContainerColor = R.color.notification_bgd
            }

            is InputCardColorState.Warning -> InputAmountColor().apply {
                borderColor = R.color.semi_transparent_yellow
                balanceContainerColor = R.color.translucent_yellow
            }

            is InputCardColorState.Valid -> InputAmountColor().apply {
                borderColor = R.color.label_text_green
                balanceContainerColor = R.color.card_background
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 8.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = colorResource(inputAmountContainerColor.borderColor),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .wrapContentSize()
                    .focusRequester(focusRequester),
                value = inputAmount,
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    color = colorResource(id = R.color.dark_blue)
                ),

                onValueChange = {
                    inputAmount = it
                    onInputChange.invoke(inputAmount.text)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    focusedLabelColor = Color.Transparent,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    cursorColor = colorResource(id = R.color.small_label_light_gray),
                ),
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Enter the amount to send",
                        style = MaterialTheme.typography.titleSmall,
                        color = colorResource(id = R.color.small_label_light_gray),
                        fontWeight = FontWeight.Normal
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),

                keyboardActions = KeyboardActions(
                    onDone = {
                        focusRequester.freeFocus()
                        keyboardController?.hide()
                    }
                ),
            )

            Text(
                text = EURO,
                style = MaterialTheme.typography.titleLarge,
                color = colorResource(id = R.color.small_label_light_gray),

                )
        }
        Divider(color = colorResource(id = R.color.notification_bgd), thickness = 1.dp)
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colorResource(id = inputAmountContainerColor.balanceContainerColor),
                    RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                )
        ) {
            Text(
                text = setUserBalance(currentBalance, EURO, inputAmountContainerColor),
                lineHeight = 18.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(start = 8.dp, top = 12.dp, bottom = 12.dp)
            )
        }
    }
}
