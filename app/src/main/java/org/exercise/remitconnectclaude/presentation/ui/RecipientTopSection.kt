package org.exercise.remitconnectclaude.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.exercise.remitconnectclaude.R
import org.exercise.remitconnectclaude.presentation.ui.recipientDetails.RecipientsTab

@Composable
fun RecipientTopSection(
    tabPosition: Int,
    popBackStack: () -> Unit,
    modifier: Modifier = Modifier,
    onTabChange: (Int) -> Unit
) {

    Column {
        IconButton(
            onClick = {
                popBackStack.invoke()
            },
            modifier = modifier
                .padding(top = 64.dp)
                .width(32.dp)
                .height(32.dp)
                .background(
                    color = colorResource(id = R.color.notification_bgd),
                    shape = RoundedCornerShape(10.dp)
                )
        ) {
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
            text = stringResource(id = R.string.recipient_header_label),
            color = colorResource(id = R.color.dark_blue),
            style = MaterialTheme.typography.headlineSmall,
            modifier = modifier
                .padding(top = 8.dp)
                .wrapContentWidth()
                .wrapContentHeight()
        )

        RecipientsTab(
            selectedTabIndex = tabPosition,
            modifier = modifier
        ) {
            onTabChange.invoke(it)
        }
    }
}