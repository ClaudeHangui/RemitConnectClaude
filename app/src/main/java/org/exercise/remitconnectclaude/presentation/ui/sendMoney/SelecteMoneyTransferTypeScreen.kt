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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import org.exercise.remitconnectclaude.R

@Composable
fun SelectMoneyToAfricaScreen(
    moneyTransferTypeId: Int,
    moneyTransferTypeName: String,
    popBackStack: () -> Unit,
    onSendMoneySelected: () -> Unit,
    sendMoneyViewModel: SendMoneyViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    ConstraintLayout {
        val (backIcon, sendTransferTypeLabel, subMoneyTransferTypes) = createRefs()

        IconButton(
            onClick = {
                popBackStack.invoke()
            },
            modifier = Modifier
                .width(32.dp)
                .height(32.dp)
                .background(
                    color = colorResource(id = R.color.notification_bgd),
                    shape = RoundedCornerShape(10.dp)
                )
                .constrainAs(backIcon) {
                    top.linkTo(parent.top, margin = 64.dp)
                    start.linkTo(parent.start, margin = 24.dp)
                }) {
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
            text = moneyTransferTypeName,
            color = colorResource(id = R.color.dark_blue),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .constrainAs(sendTransferTypeLabel) {
                    top.linkTo(backIcon.bottom, margin = 24.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .constrainAs(subMoneyTransferTypes) {
                    top.linkTo(sendTransferTypeLabel.bottom, margin = 16.dp)
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

            items(sendMoneyViewModel.methodsToSentToAfrica) { item ->
                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(16.dp)
                ) {
                    SendMoneyCard(sendMoneyType = item, onClick = { id, name ->
                        if (!name.equals("Mobile wallets", true)) {
                            Toast.makeText(context, "Feature in implementation", Toast.LENGTH_SHORT)
                                .show()
                        } else
                            onSendMoneySelected.invoke()
                    })
                }

                Divider(
                    color = colorResource(id = R.color.notification_bgd),
                    thickness = 1.dp
                )
            }
        }
    }
}