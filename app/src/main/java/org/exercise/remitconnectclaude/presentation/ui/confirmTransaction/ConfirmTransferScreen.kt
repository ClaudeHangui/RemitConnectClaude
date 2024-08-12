@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)

package org.exercise.remitconnectclaude.presentation.ui.confirmTransaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.exercise.remitconnectclaude.R
import org.exercise.remitconnectclaude.presentation.ui.Form
import org.exercise.remitconnectclaude.util.WEST_AFRICAN_FRANC

@Composable
fun ConfirmTransferScreen(formData: Form,
                          currentSheetState: SheetState, onDismiss: () -> Unit, onTransactionConfirmed: () -> Unit) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val screenHeightPx = configuration.screenHeightDp * density.density
    val screenHeightDp = with(density) { screenHeightPx.toDp() }

    // Calculate peek height as 3/4 of the screen height
    val peekHeight = screenHeightDp * 0.75f

    ModalBottomSheet(
        sheetState = currentSheetState,
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        dragHandle = {
            Column(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .width(72.dp)
                    .height(6.dp)
                    .background(colorResource(id = R.color.rounded_border_gray), shape = RoundedCornerShape(16.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BottomSheetDefaults.DragHandle()
            }
        },
        onDismissRequest = {
            onDismiss.invoke()
        }) {
        Column(
            modifier = Modifier
                .padding(start = 32.dp, end = 32.dp)
                .fillMaxWidth()
                .heightIn(min = peekHeight)
        ) {
            Text(text = stringResource(id = R.string.confirm_transfer),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Start,
                color = colorResource(id = R.color.dark_blue),
                modifier = Modifier.padding(vertical = 40.dp)
            )

            Text(text = stringResource(id = R.string.you_are_sending_label),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Normal,
                color = colorResource(id = R.color.small_label_light_gray),
                textAlign = TextAlign.Start,
            )

            Text(text = "${formData.amountReceived} $WEST_AFRICAN_FRANC",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Start,
                color = colorResource(id = R.color.dark_blue),
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(text = stringResource(id = R.string.to_label),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start,
                color = colorResource(id = R.color.small_label_light_gray),
                modifier = Modifier.padding(top = 24.dp)
            )

            Text(text = "${formData.firstName} ${formData.lastName}",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Start,
                color = colorResource(id = R.color.dark_blue),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 8.dp)
            )


            Text(text = stringResource(id = R.string.via_label),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start,
                color = colorResource(id = R.color.small_label_light_gray),
                modifier = Modifier.padding(top = 24.dp)
            )

            Text(text = "${formData.mobileMoneyProviderName} : ${formData.countryDialingCode} ${formData.phoneNumber}",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Start,
                color = colorResource(id = R.color.dark_blue),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 8.dp)
            )

            Button(
                onClick = {
                    onTransactionConfirmed.invoke()
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.label_text_green),
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues(vertical = 16.dp),
                modifier = Modifier.padding(top = 24.dp)
                    .fillMaxWidth().padding(bottom = 32.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.confirm_label),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

        }
    }
}