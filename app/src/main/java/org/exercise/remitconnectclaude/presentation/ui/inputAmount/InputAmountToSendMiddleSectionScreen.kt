package org.exercise.remitconnectclaude.presentation.ui.inputAmount

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import org.exercise.remitconnectclaude.R
import org.exercise.remitconnectclaude.presentation.theme.outfitFamily
import org.exercise.remitconnectclaude.util.EURO
import org.exercise.remitconnectclaude.util.ONE_EURO
import org.exercise.remitconnectclaude.util.WEST_AFRICAN_FRANC

@Composable
fun InputAmountToSendMiddleSectionScreen(
    amount: Amount,
    modifier: Modifier = Modifier
) {
    //val observeAmount = amount
    ConstraintLayout(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        val (freeRemittanceMsg, feeBreakDownLabel, monecoFeeLabel, monecoFee,
            transferFeeLabel, transferFee,
            conversionRateLabel, conversionRate,
            totalSpentLabel, totalSpent,
            dashDivider, recipientReceivesLabel, recipientReceive) = createRefs()


        Text(
           text = setRemittanceMessage(),
            modifier = Modifier
                .padding(top = 24.dp)
                .wrapContentWidth()
                .wrapContentHeight()
                .constrainAs(freeRemittanceMsg) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )

        Text(
            text = stringResource(id = R.string.fees_breakdown),
            style = MaterialTheme.typography.bodyLarge,
            color = colorResource(id = R.color.dark_blue),
            modifier = Modifier.constrainAs(feeBreakDownLabel) {
                top.linkTo(freeRemittanceMsg.bottom, margin = 40.dp)
                start.linkTo(parent.start)
            }
        )

        Text(
            text = stringResource(id = R.string.moneco_fees),
            style = MaterialTheme.typography.titleSmall,
            color = colorResource(id = R.color.small_label_light_gray),
            modifier = Modifier.constrainAs(monecoFeeLabel) {
                top.linkTo(feeBreakDownLabel.bottom, margin = 20.dp)
                start.linkTo(parent.start)
            }
        )

        Text(
            text = "${amount.monecoFees} $EURO",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Normal,
            color = colorResource(id = R.color.dark_blue),
            modifier = Modifier.constrainAs(monecoFee) {
                top.linkTo(monecoFeeLabel.top)
                bottom.linkTo(monecoFeeLabel.bottom)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = stringResource(id = R.string.transfer_fees),
            style = MaterialTheme.typography.titleSmall,
            color = colorResource(id = R.color.small_label_light_gray),
            modifier = Modifier.constrainAs(transferFeeLabel) {
                top.linkTo(monecoFeeLabel.bottom, margin = 24.dp)
                start.linkTo(parent.start)
            }
        )

        Text(
            text = "0.0 $EURO",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Normal,
            color = colorResource(id = R.color.dark_blue),
            modifier = Modifier.constrainAs(transferFee) {
                top.linkTo(transferFeeLabel.top)
                bottom.linkTo(transferFeeLabel.bottom)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = stringResource(id = R.string.conversion_rate),
            style = MaterialTheme.typography.titleSmall,
            color = colorResource(id = R.color.small_label_light_gray),
            modifier = Modifier.constrainAs(conversionRateLabel) {
                top.linkTo(transferFeeLabel.bottom, margin = 24.dp)
                start.linkTo(parent.start)
            }
        )

        Text(
            text = "$ONE_EURO $WEST_AFRICAN_FRANC",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Normal,
            color = colorResource(id = R.color.dark_blue),
            modifier = Modifier.constrainAs(conversionRate) {
                top.linkTo(conversionRateLabel.top)
                bottom.linkTo(conversionRateLabel.bottom)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = stringResource(id = R.string.total_to_spend),
            style = MaterialTheme.typography.titleSmall,
            color = colorResource(id = R.color.small_label_light_gray),
            modifier = Modifier.constrainAs(totalSpentLabel) {
                top.linkTo(conversionRateLabel.bottom, margin = 24.dp)
                start.linkTo(parent.start)
            }
        )

        Text(
            text = "${amount.amountSpent} $EURO",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Normal,
            color = colorResource(id = R.color.dark_blue),
            modifier = Modifier.constrainAs(totalSpent) {
                top.linkTo(totalSpentLabel.top)
                bottom.linkTo(totalSpentLabel.bottom)
                end.linkTo(parent.end)
            }
        )

        DashedLine(
            modifier = Modifier.constrainAs(dashDivider){
                top.linkTo(totalSpent.bottom, margin = 32.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = stringResource(id = R.string.recipient_gets),
            style = MaterialTheme.typography.titleSmall,
            color = colorResource(id = R.color.small_label_light_gray),
            modifier = Modifier.constrainAs(recipientReceivesLabel) {
                top.linkTo(dashDivider.bottom, margin = 36.dp)
                start.linkTo(parent.start)
            }
        )

        Text(
            text = "${amount.amountReceived} $WEST_AFRICAN_FRANC",
            style = MaterialTheme.typography.titleLarge,
            color = colorResource(id = R.color.dark_blue),
            modifier = Modifier.constrainAs(recipientReceive) {
                top.linkTo(recipientReceivesLabel.top)
                bottom.linkTo(recipientReceivesLabel.bottom)
                end.linkTo(parent.end)
            }
        )
    }
}

@Composable
fun setRemittanceMessage() = buildAnnotatedString {
    withStyle(
        style = SpanStyle(
            color = colorResource(id = R.color.dark_blue),
            fontFamily = outfitFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
        )
    ){
        append(stringResource(id = R.string.yearly_remittance))
    }
    appendLine()
    withStyle(
        style = SpanStyle(
            color = colorResource(id = R.color.small_label_light_gray),
            fontFamily = outfitFamily,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
            )
    ){
        append(stringResource(id = R.string.remittance_are_free))
    }

    appendLine()
    withStyle(
        style = SpanStyle(
            color = colorResource(id = R.color.light_blue),
            fontFamily = outfitFamily,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    ){
        append(stringResource(id = R.string.check_remaining_remittance))
    }
}

@Composable
fun DashedLine(modifier: Modifier = Modifier) {
    // Set the margin around the dashed line
    //val marginHorizontal = 16.dp

    // Use a Row to hold the dashes
    Row(
        modifier = modifier
            .fillMaxWidth() // Fill the entire width of the parent
            //.padding(horizontal = marginHorizontal)
        ,
        horizontalArrangement = Arrangement.SpaceBetween // Distribute dashes evenly
    ) {
        // Calculate the number of dashes based on the screen width
        val screenWidth = LocalConfiguration.current.screenWidthDp.dp //- (marginHorizontal * 2)
        val dashWidth = 5.dp // Width of each dash
        val dashCount = (screenWidth / (dashWidth + 5.dp)).toInt()

        // Create dashes
        repeat(dashCount) {
            Box(
                modifier = Modifier
                    .height(1.dp)
                    .width(dashWidth)
                    .background(colorResource(id = R.color.small_label_light_gray)) // Adjust the color of each dash
            )
        }
    }
}