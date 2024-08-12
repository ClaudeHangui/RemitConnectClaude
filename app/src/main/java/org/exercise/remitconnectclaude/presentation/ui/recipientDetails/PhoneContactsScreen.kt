package org.exercise.remitconnectclaude.presentation.ui.recipientDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.exercise.remitconnectclaude.R
import org.exercise.remitconnectclaude.data.Recipient
import org.exercise.remitconnectclaude.presentation.ui.MySearchBar

@Composable
fun PreviousRecipientScreen(
    modifier: Modifier = Modifier,
    query: String,
    phoneContacts: List<Recipient>,
    onQueryChange: (String) -> Unit,
    onContactSelected: (recipient: Recipient) -> Unit
) {

    val filteredResults = remember(query, phoneContacts) {
        if (query.isEmpty()){
            phoneContacts
        } else {
            phoneContacts.filter {
                    it.firstName.contains(query, ignoreCase = true) ||
                            it.lastName.contains(query, ignoreCase = true)
                }
        }
    }

    Column {

        MySearchBar(
            hint = stringResource(id = R.string.search_contact),
            modifier = Modifier
                .fillMaxWidth().padding(horizontal = 24.dp),
            searchQuery = query,
            onQueryChange = onQueryChange
        )

        Text(
            text = stringResource(id = R.string.contacts_on_phone),
            color = Color.Black,
            style = MaterialTheme.typography.titleSmall,
            modifier = modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(start = 24.dp)
        )

        Divider(
            color = colorResource(id = R.color.notification_bgd),
            thickness = 1.dp,
            modifier = modifier.fillMaxWidth().padding(top = 4.dp)
        )

        if (filteredResults.isEmpty()){
            NoResultsScreen(modifier = Modifier.fillMaxWidth(), EmptyScreenState.PhoneContact)
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                items(filteredResults) { item ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 24.dp, vertical = 16.dp)
                            .wrapContentHeight()
                    ) {
                        PreviousRecipientCard(user = item) { recipient ->
                            onContactSelected.invoke(recipient)
                        }
                    }

                    Divider(
                        color = colorResource(id = R.color.notification_bgd),
                        thickness = 1.dp
                    )
                }
            }
        }
    }
}