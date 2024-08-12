package org.exercise.remitconnectclaude.presentation.ui.sendMoney

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import org.exercise.remitconnectclaude.R
import org.exercise.remitconnectclaude.presentation.ui.utils.SendMoneyType

@Composable
fun SendMoneyCard(
    sendMoneyType: SendMoneyType,
    onClick: (selectedItemId: Int, name: String) -> Unit
) {
    ConstraintLayout(modifier = Modifier.fillMaxWidth().clickable {
        onClick(sendMoneyType.typeId, sendMoneyType.typeName)
    }) {
        val (icon, label, arrow) = createRefs()

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(
                    color = colorResource(id = R.color.card_background),
                    shape = RoundedCornerShape(12.dp)
                )
                .width(40.dp)
                .height(40.dp)
                .constrainAs(icon){
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }

        ) {
            Icon(painter = painterResource(id = sendMoneyType.typeIcon), 
                tint = colorResource(id = R.color.label_text_green),
                contentDescription = null)
        }

        Text(
            text = sendMoneyType.typeName,
            color = colorResource(id = R.color.dark_blue),
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 20.sp,
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(start = 12.dp)
                .constrainAs(label){
                    top.linkTo(icon.top)
                    bottom.linkTo(icon.bottom)
                    start.linkTo(icon.end, margin = 16.dp)
                }
        )

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Arrow Forward",
            tint = colorResource(id = R.color.dark_blue),
            modifier = Modifier
                .padding(start = 8.dp)
                .width(18.dp)
                .height(18.dp)
                .constrainAs(arrow){
                    top.linkTo(icon.top)
                    bottom.linkTo(icon.bottom)
                    end.linkTo(parent.end)
                }
        )
    }
}