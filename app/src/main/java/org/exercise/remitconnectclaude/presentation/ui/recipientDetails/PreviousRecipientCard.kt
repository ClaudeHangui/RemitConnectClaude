package org.exercise.remitconnectclaude.presentation.ui.recipientDetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import org.exercise.remitconnectclaude.R
import org.exercise.remitconnectclaude.data.Recipient

@Composable
fun PreviousRecipientCard(user: Recipient,
                          onClick: (Recipient) -> Unit
){
    ConstraintLayout(modifier = Modifier
        .wrapContentHeight()
        .fillMaxWidth()
        .clickable { onClick(user) }
    ) {
        val (picture, fullName, number, rightArrow) = createRefs()

        AsyncImage(
            model = "",
            contentDescription = "profile picture image",
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.ic_moneco_image_placeholder),
            error = painterResource(id = R.drawable.ic_moneco_image_placeholder),
            modifier = Modifier
                .wrapContentWidth()
                .clip(RoundedCornerShape(12.dp))
                .constrainAs(picture) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .size(44.dp)

        )

        Text(
            text = "${user.firstName} ${user.lastName}",
            color = colorResource(id = R.color.dark_blue),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            softWrap = true,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .constrainAs(fullName) {
                    top.linkTo(picture.top)
                    start.linkTo(picture.end, margin = 12.dp)
                    end.linkTo(rightArrow.start, margin = 12.dp)
                    width = Dimension.fillToConstraints
                }

        )

        Text(
            text = "${user.dialingCode} ${user.phoneNumber}",
            color = colorResource(id = R.color.small_label_light_gray),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .wrapContentSize()
                .constrainAs(number) {
                    bottom.linkTo(picture.bottom)
                    start.linkTo(picture.end, margin = 12.dp)
                }
        )

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Arrow Forward",
            tint = colorResource(id = R.color.dark_blue),
            modifier = Modifier
                .padding(end = 8.dp)
                .size(18.dp)
                .constrainAs(rightArrow) {
                    top.linkTo(picture.top)
                    bottom.linkTo(picture.bottom)
                    end.linkTo(parent.end)
                }
        )

    }
}