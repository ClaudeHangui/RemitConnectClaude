package org.exercise.remitconnectclaude.presentation.ui.homeNavigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.exercise.remitconnectclaude.R
import org.exercise.remitconnectclaude.presentation.ui.utils.SupportedInternalOperationItem

@Composable
fun InternalOperationCard(
    modifier: Modifier = Modifier,
    item: SupportedInternalOperationItem,
    onClick: (itemId: Int) -> Unit
){
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .clickable { onClick(item.operationId) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = item.operationIcon),
                contentDescription = null)

            Text(
                text = item.operationName,
                color = colorResource(id = R.color.label_text_green),
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .padding(top = 12.dp)
            )
        }
    }
}