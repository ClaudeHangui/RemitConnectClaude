package org.exercise.remitconnectclaude.presentation.ui.recipientDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.exercise.remitconnectclaude.R

@Composable
fun PickRecipientButton(modifier: Modifier = Modifier) {
    Row(modifier = modifier
        .padding(top = 16.dp)
        .fillMaxWidth()
        .background(
            colorResource(id = R.color.pick_contact_green),
            shape = RoundedCornerShape(8.dp)
        )
        .border(
            width = 1.dp,
            shape = RoundedCornerShape(8.dp),
            color = colorResource(id = R.color.card_background)
        )
        .padding(vertical = 16.dp)
        ,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painterResource(id = R.drawable.ic_moneco_contact)
            ,contentDescription = null)

        Spacer(modifier = Modifier.width(8.dp))

        Text(text = stringResource(id = R.string.choose_contact_label),
            color = colorResource(id = R.color.label_text_green),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}