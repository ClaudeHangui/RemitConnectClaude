package org.exercise.remitconnectclaude.presentation.ui.selectWallet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.exercise.remitconnectclaude.R

@Composable
fun MobileWalletCard(
    mobileMoneyProvider: MobileMoneyProvider,
    onSelect: (id: String, name: String) -> Unit,
    isSelected: Boolean,
) {
    val borderColor = if (isSelected) colorResource(id = R.color.label_text_green)
    else colorResource(id = R.color.rounded_border_gray)

    Row(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(12.dp)
            )

            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp)
            .clickable {
                onSelect(
                    mobileMoneyProvider.providerId,
                    mobileMoneyProvider.providerName
                )
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = mobileMoneyProvider.providerLogo),
            modifier = Modifier
                .wrapContentWidth()
                .clip(RoundedCornerShape(12.dp))
                .height(40.dp)
                .width(40.dp),
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = mobileMoneyProvider.providerName,
            color = colorResource(id = R.color.dark_blue),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
        )
    }
}