@file:OptIn(ExperimentalAnimationApi::class)

package org.exercise.remitconnectclaude.presentation.ui.selectWallet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.exercise.remitconnectclaude.R
import org.exercise.remitconnectclaude.presentation.ui.SendMoneyToAfricaViewModel
import org.exercise.remitconnectclaude.presentation.ui.utils.FailureState
import org.exercise.remitconnectclaude.presentation.ui.utils.ShowLoader

@Composable
fun SelectMobileWalletScreen(
    popBackStack: () -> Unit,
    onProviderSelected: () -> Unit,
    mobileWalletViewModel: MobileWalletViewModel = hiltViewModel(),
    sendMoneyToAfricaViewModel: SendMoneyToAfricaViewModel
) {

    val state by mobileWalletViewModel.mobileWalletFlow.collectAsStateWithLifecycle()
    var mobileMoneyProviderId by rememberSaveable { mutableStateOf("") }
    var mobileMoneyProviderName by rememberSaveable { mutableStateOf("") }
    var shouldEnableButton by rememberSaveable {
        mutableStateOf(true)
    }

    LaunchedEffect(state.isProviderSelected){
        if (state.isProviderSelected){
            sendMoneyToAfricaViewModel.saveProviderSelected(
                mobileMoneyProviderId,
                mobileMoneyProviderName
            )
            onProviderSelected.invoke()
            mobileWalletViewModel.resetState()
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (backPressIcon, selectWalletLabel, walletList, bottomDivider, errorBox, continueFlowBtn) = createRefs()

        IconButton(
            onClick = {
                popBackStack.invoke()
            },
            modifier = Modifier
                .padding(top = 64.dp)
                .width(32.dp)
                .height(32.dp)
                .background(
                    color = colorResource(id = R.color.notification_bgd),
                    shape = RoundedCornerShape(10.dp)
                )
                .constrainAs(backPressIcon) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start, margin = 24.dp)
                    start.linkTo(parent.start, margin = 24.dp)
                }
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
            text = stringResource(id = R.string.choose_wallet_title),
            color = colorResource(id = R.color.dark_blue),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(top = 24.dp)
                .wrapContentWidth()
                .wrapContentHeight()
                .constrainAs(selectWalletLabel) {
                    top.linkTo(backPressIcon.bottom)
                    start.linkTo(backPressIcon.start)
                }
        )

        Box(modifier = Modifier.constrainAs(errorBox){
            top.linkTo(selectWalletLabel.bottom)
            bottom.linkTo(parent.bottom)
        }) {
            state.apply {
                if (loading){
                    ShowLoader()
                }

                AnimatedVisibility(visible = throwable != null && providers.isEmpty()) {
                    shouldEnableButton = false
                    Box {
                        FailureState(
                            customErrorMsg = R.string.turn_online_to_get_wallets,
                            throwable = throwable, data = providers){
                            mobileWalletViewModel.getAllWallets()
                        }
                    }
                }
            }
        }


        Box(modifier = Modifier
            .padding(bottom = 24.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .constrainAs(walletList) {
                top.linkTo(selectWalletLabel.bottom)
            }) {

            state.apply {
                AnimatedVisibility(visible = providers.isNotEmpty()){
                    shouldEnableButton = true
                    LazyColumn(
                        modifier = Modifier
                            .padding(bottom = 24.dp, start = 24.dp, end = 24.dp)
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        items(state.providers) { item ->
                            MobileWalletCard(item, onSelect = { id, name ->
                                mobileMoneyProviderId = id
                                mobileMoneyProviderName = name
                                mobileWalletViewModel.onProviderSelected(mobileMoneyProviderId, mobileMoneyProviderName)
                            },
                                isSelected = state.selectedProviderId == item.providerId
                            )
                        }
                    }
                }
            }
        }

        Divider(
            color = colorResource(id = R.color.light_gray),
            modifier = Modifier
                .height(5.dp)
                .fillMaxWidth()
                .constrainAs(bottomDivider) {
                    bottom.linkTo(continueFlowBtn.top, margin = 12.dp)
                }
        )

        AnimatedVisibility(visible = state.providers.isNotEmpty(),
            modifier = Modifier.constrainAs(continueFlowBtn) {
                bottom.linkTo(parent.bottom, margin = 24.dp)

            }
        ){
            Button(
                onClick = {
                    mobileWalletViewModel.hasMobileProviderBeenSelected(mobileMoneyProviderId, mobileMoneyProviderName)
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.label_text_green),
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues(vertical = 16.dp),
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
                    .fillMaxWidth()

            ) {
                Text(
                    text = stringResource(id = R.string.continue_label),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}