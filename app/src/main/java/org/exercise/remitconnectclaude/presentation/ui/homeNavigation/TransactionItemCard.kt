package org.exercise.remitconnectclaude.presentation.ui.homeNavigation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import org.exercise.remitconnectclaude.R
import org.exercise.remitconnectclaude.data.Transaction
import org.exercise.remitconnectclaude.util.EURO
import org.exercise.remitconnectclaude.util.EURO_SYMBOL

@Composable
fun TransactionItemCard(
    modifier: Modifier = Modifier,
    item: Transaction
){
    ConstraintLayout(modifier = modifier
        .padding(vertical = 1.dp)
        .background(
            color = Color.White,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        )
        .padding( 16.dp)
        .fillMaxWidth()
        .wrapContentHeight()

    ){
        Log.e("TransactionItemCard", "Recipient name: $item")

        val (sendIcon, sentToLabel, recipientName, amountSent) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.ic_moneco_avatar),
            contentDescription = null,
            modifier = Modifier.constrainAs(sendIcon){
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
            })

        Text(
            text = stringResource(id = R.string.sent_to_label),
            color = colorResource(id = R.color.small_label_light_gray),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .constrainAs(sentToLabel){
                    top.linkTo(sendIcon.top)
                    start.linkTo(sendIcon.end, margin = 8.dp)
                }
        )

        Text(
            text = item.recipientName,
            color = colorResource(id = R.color.dark_blue),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .constrainAs(recipientName){
                    bottom.linkTo(sendIcon.bottom)
                    start.linkTo(sendIcon.end, margin = 8.dp)
                }
        )

        Text(
            text = "$EURO_SYMBOL ${item.amountSent}",
            color = colorResource(id = R.color.dark_blue),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .constrainAs(amountSent){
                    end.linkTo(parent.end)
                    top.linkTo(sendIcon.top)
                    bottom.linkTo(sendIcon.bottom)
                }
        )
    }
}