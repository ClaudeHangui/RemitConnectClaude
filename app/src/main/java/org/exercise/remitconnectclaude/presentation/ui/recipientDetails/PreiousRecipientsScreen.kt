package org.exercise.remitconnectclaude.presentation.ui.recipientDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
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
fun NoResultsScreen(
    modifier: Modifier = Modifier,
    emptyScreenState: EmptyScreenState
) {
    val message = when(emptyScreenState){
        is EmptyScreenState.PreviousRecipients -> R.string.no_previous_recipients
        is EmptyScreenState.PhoneContact -> R.string.filter_empty
        is EmptyScreenState.PreviousTransactions -> R.string.no_previous_transactions
    }


    Column(modifier = modifier.padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Image(painter = painterResource(id = R.drawable.ic_moneco_empty_result),
            contentDescription = null)

        Text(
            text = stringResource(id = message),
            color = colorResource(id = R.color.medium_title_color),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .padding(top = 12.dp)
                .wrapContentWidth()
                .wrapContentHeight()
        )
    }
}

sealed class EmptyScreenState {
    object PhoneContact: EmptyScreenState()
    object PreviousTransactions: EmptyScreenState()
    object PreviousRecipients: EmptyScreenState()
}