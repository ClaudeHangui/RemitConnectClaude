package org.exercise.remitconnectclaude.presentation.ui.recipientDetails

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import org.exercise.remitconnectclaude.R

@Composable
fun RecipientsTab(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    onTabChange: (Int) -> Unit
) {
    val tabTitles = listOf(
        stringResource(R.string.previous_recipients),
        stringResource(R.string.new_recipient)
    )

    TabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = colorResource(id = R.color.card_background),
        modifier = modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .height(48.dp),
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                height = 40.dp,
                color = colorResource(id = R.color.green),
                modifier = Modifier
                    .tabIndicatorOffset(tabPositions[selectedTabIndex])
                    .zIndex(-1f)
                    .padding(4.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
            )
        },

        divider = {
            Color.Transparent
        }
    ) {
        tabTitles.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = {
                    onTabChange.invoke(index)
                },
                selectedContentColor = Color.White,
                unselectedContentColor = colorResource(
                    id = R.color.label_text_green
                ),
                text = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            )
        }
    }
}