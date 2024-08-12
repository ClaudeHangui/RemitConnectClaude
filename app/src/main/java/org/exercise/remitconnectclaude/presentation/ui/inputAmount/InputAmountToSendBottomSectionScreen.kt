@file:OptIn(ExperimentalMaterial3Api::class)

package org.exercise.remitconnectclaude.presentation.ui.inputAmount

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.exercise.remitconnectclaude.R

@Composable
fun InputAmountToSendBottomSectionScreen(
    cardColorState: InputCardColorState,
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit
) {

    Column(modifier = modifier
        .fillMaxWidth()
        .wrapContentHeight()
    ) {

        Divider(
            color = colorResource(id = R.color.light_gray),
            modifier = Modifier
                .height(5.dp)
                .fillMaxWidth()
        )

        Button(
            onClick = {
                onButtonClick.invoke()
            },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.label_text_green),
                contentColor = Color.White,
                disabledContentColor = Color.White,
                disabledContainerColor = colorResource(id = R.color.label_text_green).copy(alpha = 0.1f)
            ),
            contentPadding = PaddingValues(vertical = 16.dp),
            modifier = Modifier
                .padding(top = 16.dp, start = 24.dp, end = 24.dp, bottom = 24.dp)
                .fillMaxWidth(),
            enabled = cardColorState is InputCardColorState.Valid
        ) {
            Text(
                text = stringResource(id = R.string.send_label),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }
    }
}