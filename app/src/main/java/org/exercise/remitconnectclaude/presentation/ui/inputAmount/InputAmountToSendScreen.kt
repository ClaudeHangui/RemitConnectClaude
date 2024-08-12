package org.exercise.remitconnectclaude.presentation.ui.inputAmount

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import org.exercise.remitconnectclaude.presentation.ui.SendMoneyToAfricaViewModel
import org.exercise.remitconnectclaude.presentation.ui.confirmTransaction.ConfirmTransferScreen
import org.exercise.remitconnectclaude.presentation.ui.utils.ShowLoader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputAmountToSendScreen(
    navController: NavHostController,
    viewModel: ValidateInputAmountViewModel = hiltViewModel(),
    sendMoneyToAfricaViewModel: SendMoneyToAfricaViewModel
) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }

    val state = viewModel.amountToSpendState.collectAsStateWithLifecycle()
    val transactionDataState = sendMoneyToAfricaViewModel.formState.collectAsStateWithLifecycle()

    var amountSentInEuros by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = transactionDataState.value){
        if (transactionDataState.value.isTransactionSuccessful){
            sendMoneyToAfricaViewModel.resetState()
            navController.navigate(Destinations.SuccessfulTransactionScreen.route)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        InputAmountToSendTopSectionScreen(
            onBackButtonClicked = {
                navController.popBackStack()
            },
            isSelected = false,
            modifier = Modifier
                .fillMaxWidth()
        )

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f, false)
                .padding(bottom = 120.dp)

        ) {
            BalanceCard(state.value.currentBalance,
                state.value.inputCardColorState,
                onInputChange = { amount ->
                    amountSentInEuros = amount
                    viewModel.calculateAmountToSpend(amount, state.value.currentBalance)
                }
            )

            if (transactionDataState.value.loading){
                ShowLoader()
            }

            InputAmountToSendMiddleSectionScreen(
                state.value.amount,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )

            Spacer(modifier = Modifier.weight(1f))
        }


        InputAmountToSendBottomSectionScreen(
            state.value.inputCardColorState,
            modifier = Modifier
                .padding(bottom = 24.dp)
                .fillMaxWidth()
        ) {
            openBottomSheet = !openBottomSheet
        }
    }

    if (openBottomSheet) {
        sendMoneyToAfricaViewModel.updateAmountProvided(state.value.amount.amountReceived)
        ConfirmTransferScreen(
            formData = transactionDataState.value.input,
            currentSheetState = bottomSheetState,
            onDismiss = {
                openBottomSheet = false
            }) {
            openBottomSheet = false
            transactionDataState.value.input.apply {
                sendMoneyToAfricaViewModel.simulateTransaction(firstName, lastName, amountSentInEuros)
            }
        }
    }
}