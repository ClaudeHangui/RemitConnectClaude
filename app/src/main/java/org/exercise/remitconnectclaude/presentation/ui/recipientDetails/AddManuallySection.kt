package org.exercise.remitconnectclaude.presentation.ui.recipientDetails

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.exercise.remitconnectclaude.R

@Composable
fun OrAddManually() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 32.dp, horizontal = 24.dp)
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Divider(
            color = colorResource(id = R.color.border_gray),
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(id = R.string.add_manually_label),
            color = colorResource(id = R.color.small_label_light_gray),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(8.dp))
        Divider(
            color = colorResource(id = R.color.border_gray),
            modifier = Modifier.weight(1f)
        )
    }
}