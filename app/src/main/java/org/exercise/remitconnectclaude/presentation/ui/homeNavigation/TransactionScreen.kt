package org.exercise.remitconnectclaude.presentation.ui.homeNavigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.exercise.remitconnectclaude.R
import org.exercise.remitconnectclaude.data.Transaction
import org.exercise.remitconnectclaude.presentation.HomeViewModel
import org.exercise.remitconnectclaude.presentation.ui.utils.SupportedInternalOperationItem

@Composable
fun TransactionScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val state by homeViewModel.homeState.collectAsStateWithLifecycle()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.main_bgd))
            .padding(top = 64.dp)
    ) {
        val (userName, notificationIcon, balanceInfo) = createRefs()

        Text(
            text = stringResource(id = R.string.hello_user, state.userName),
            color = colorResource(id = R.color.dark_blue),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .constrainAs(userName) {
                    top.linkTo(parent.top, margin = 32.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                }
        )

        IconButton(
            onClick = { },
            modifier = Modifier
                .background(
                    colorResource(id = R.color.notification_bgd),
                    shape = RoundedCornerShape(12.dp)
                )
                .width(40.dp)
                .height(40.dp)
                .constrainAs(notificationIcon) {
                    top.linkTo(userName.top)
                    bottom.linkTo(userName.bottom)
                    end.linkTo(parent.end, margin = 16.dp)
                }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_bell_blue),
                contentDescription = null
            )
        }

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .constrainAs(balanceInfo) {
                    top.linkTo(userName.bottom, margin = 16.dp)
                    start.linkTo(userName.start)
                    end.linkTo(notificationIcon.end)
                }) {


            item {
                Box(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .background(
                            alpha = 0.9f,
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    colorResource(id = R.color.color_one),
                                    colorResource(id = R.color.color_two)
                                )
                            ),
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    // Add content for the rectangular box here if needed
                    ConstraintLayout(modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                        .fillMaxWidth()) {
                        val (balanceLabel, balanceIcon, balanceInfo, userCurrency) = createRefs()

                        Text(
                            text = stringResource(id = R.string.my_balance),
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .wrapContentWidth()
                                .wrapContentHeight()
                                .constrainAs(balanceLabel) {
                                    top.linkTo(parent.top, margin = 12.dp)
                                    start.linkTo(parent.start)
                                }
                        )

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .width(56.dp)
                                .height(56.dp)
                                .background(
                                    color = colorResource(id = R.color.color_one),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .constrainAs(balanceIcon) {
                                    top.linkTo(parent.top, margin = 8.dp)
                                    end.linkTo(parent.end)
                                }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_balance_white),
                                contentDescription = null)
                        }


                        Text(
                            text = state.userBalance,
                            color = Color.White,
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier
                                .wrapContentWidth()
                                .wrapContentHeight()
                                .constrainAs(balanceInfo) {
                                    top.linkTo(balanceIcon.bottom, margin = 12.dp)
                                    start.linkTo(balanceLabel.start)
                                }
                        )

                        Text(
                            text = state.userCurrency,
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier
                                .wrapContentWidth()
                                .wrapContentHeight()
                                .constrainAs(userCurrency) {
                                    top.linkTo(balanceInfo.bottom, margin = 4.dp)
                                    start.linkTo(balanceLabel.start)
                                }
                        )
                    }
                }
            }


            val internalOperations = state.internalOperations

            items(internalOperations.chunked(2)) { rowItems ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    for (item in rowItems) {
                        InternalOperationCard(
                            modifier = Modifier.weight(1f),
                            item = item, onClick = {})
                    }

                    if (rowItems.size == 1) {
                        Spacer(modifier = Modifier.weight(1f)) // Fill the remaining space if there is only one item in the row
                    }
                }
            }

            item {
                Text(
                    text = stringResource(id = R.string.transactions_label),
                    color = colorResource(id = R.color.dark_blue),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .wrapContentWidth()
                        .wrapContentHeight()
                )
            }

            val latestTransactions = state.latestTransactions

            item {
                latestTransactions.forEach {
                    TransactionItemCard(modifier = Modifier,item = it)
                }
            }

            item {
                Spacer(modifier = Modifier.padding(32.dp))
            }

        }
    }
}


private fun LazyListScope.gridItems(
    data: List<SupportedInternalOperationItem>,
    columnCount: Int,
    modifier: Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    itemContent: @Composable BoxScope.(SupportedInternalOperationItem) -> Unit,
) {
    val size = data.count()
    val rows = if (size == 0) 0 else 1 + (size - 1) / columnCount
    items(rows, key = { it.hashCode() }) { rowIndex ->
        Row(
            horizontalArrangement = horizontalArrangement,
            modifier = modifier
        ) {
            for (columnIndex in 0 until columnCount) {
                val itemIndex = rowIndex * columnCount + columnIndex
                if (itemIndex < size) {
                    Box(
                        modifier = Modifier.weight(1F, fill = true),
                        propagateMinConstraints = true
                    ) {
                        itemContent(data[itemIndex])
                    }
                } else {
                    Spacer(Modifier.weight(1F, fill = true))
                }
            }
        }
    }
}

@Composable
fun GridList(items: List<String>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items.chunked(2)) { rowItems ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                for (item in rowItems) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f) // Ensure square shape for grid items
                            .background(Color.LightGray)
                    ) {
                        Text(
                            text = item,
                            modifier = Modifier.align(Alignment.Center),
                            color = Color.Black
                        )
                    }
                }
                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f)) // Fill the remaining space if there is only one item in the row
                }
            }
        }
    }
}