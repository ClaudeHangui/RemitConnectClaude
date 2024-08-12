package org.exercise.remitconnectclaude.presentation.ui.sendMoney

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import org.exercise.remitconnectclaude.R
import org.exercise.remitconnectclaude.presentation.ui.sendMoney.SendMoneyCard
import org.exercise.remitconnectclaude.presentation.ui.sendMoney.SendMoneyViewModel

@Composable
fun SendMoneyScreen(navigateBack: () -> Unit,
                    onSendMoneyTypeSelected: (Int, String) -> Unit,
                    sendMoneyViewModel: SendMoneyViewModel = hiltViewModel()) {

    val context = LocalContext.current
    ConstraintLayout {
        val (closeIcon, sendMoneyLabel, moneyTransferTypes) = createRefs()

        IconButton(
            onClick = {
                navigateBack.invoke()
            },
            modifier = Modifier
                .width(32.dp).height(32.dp)
                .background(
                    color = colorResource(id = R.color.notification_bgd),
                    shape = RoundedCornerShape(10.dp)
                )
                .constrainAs(closeIcon) {
                    top.linkTo(parent.top, margin = 64.dp)
                    end.linkTo(parent.end, margin = 24.dp)
                }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_moneco_close),
                contentDescription = null
            )
        }

        Text(
            text = stringResource(id = R.string.send_money),
            color = colorResource(id = R.color.dark_blue),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .constrainAs(sendMoneyLabel) {
                    top.linkTo(closeIcon.bottom, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                }
        )

        LazyColumn (
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .constrainAs(moneyTransferTypes) {
                    top.linkTo(sendMoneyLabel.bottom, margin = 16.dp)
                }
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)  // Optional padding around the Divider
                ) {
                    Divider(
                        color = colorResource(id = R.color.notification_bgd),
                        thickness = 1.dp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            items(sendMoneyViewModel.moneyTransferMethods) { item ->
                Box(modifier = Modifier
                    .wrapContentHeight()
                    .padding(16.dp)){
                    SendMoneyCard(sendMoneyType = item, onClick = { id, name ->
                        if (sendMoneyViewModel.checkIfModeIsAvailable(id)) {
                            onSendMoneyTypeSelected(id, name)
                        } else {
                            Toast.makeText(context, "Feature not implemented", Toast.LENGTH_SHORT).show()
                        }
                    })
                }

                Divider(color = colorResource(id = R.color.notification_bgd),
                    thickness = 1.dp)
            }
        }
    }
}