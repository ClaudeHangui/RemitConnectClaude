package org.exercise.remitconnectclaude.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.exercise.remitconnectclaude.R
import org.exercise.remitconnectclaude.presentation.ui.recipientDetails.CountrySpinner
import org.exercise.remitconnectclaude.presentation.ui.recipientDetails.AddRecipientViaFormScreen
import org.exercise.remitconnectclaude.presentation.ui.recipientDetails.InputFormValidationState
import org.exercise.remitconnectclaude.presentation.ui.recipientDetails.OrAddManually
import org.exercise.remitconnectclaude.presentation.ui.recipientDetails.PickRecipientButton
import org.exercise.remitconnectclaude.presentation.ui.recipientDetails.RecipientViewModel

@Composable
fun AddRecipientScreen(
    modifier: Modifier = Modifier,
    onFormValid: (String, String, String, String, String) -> Unit,
    onChangeTab: (Int) -> Unit,
    recipientViewModel: RecipientViewModel,
    sendMoneyToAfricaViewModel: SendMoneyToAfricaViewModel
) {

    val validationState by recipientViewModel.validationState.collectAsStateWithLifecycle()
    val inputDate by sendMoneyToAfricaViewModel.formState.collectAsStateWithLifecycle()

    var recipientCountryDialingCode by rememberSaveable { mutableStateOf(inputDate.input.countryDialingCode) }
    var recipientCountryName by rememberSaveable { mutableStateOf(inputDate.input.countryName) }
    var recipientFirstName by rememberSaveable { mutableStateOf(inputDate.input.firstName) }
    var recipientLastName by rememberSaveable { mutableStateOf(inputDate.input.lastName) }
    var recipientPhoneNumber by rememberSaveable { mutableStateOf(inputDate.input.phoneNumber) }
    var setContinueBtnStatus by rememberSaveable { mutableStateOf(true) }


    LaunchedEffect(validationState) {
        if (validationState is InputFormValidationState.Success) {
            onFormValid.invoke(
                recipientFirstName,
                recipientLastName,
                recipientCountryDialingCode,
                recipientPhoneNumber,
                recipientCountryName
            )
            recipientViewModel.resetValidationState()
        }
    }

    Column(modifier = modifier.verticalScroll(rememberScrollState())) {

        CountrySpinner(
            countryName = recipientCountryName,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            onCountryChange = { countryDialingCode, countryName ->
                recipientCountryDialingCode = countryDialingCode
                recipientCountryName = countryName

            },
            onNoCountryAvailable = {
                setContinueBtnStatus = it
            }
        )

        PickRecipientButton(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .clickable {
                    onChangeTab.invoke(0)
                }
        )

        OrAddManually()

        AddRecipientViaFormScreen(
            inputDate.input,
            validationState,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),

            onFirstNameChange = {
                recipientFirstName = it
            },
            onLastNameChange = {
                recipientLastName = it
            },
            onPhoneNumberChange = {
                recipientPhoneNumber = it
            }
        )

        Divider(
            color = colorResource(id = R.color.light_gray),
            modifier = Modifier
                .height(5.dp)
                .fillMaxWidth()
        )

        Button(
            onClick = {
                recipientViewModel.validateInput(
                    recipientFirstName, recipientLastName, recipientPhoneNumber,
                    recipientCountryDialingCode
                )
            },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.label_text_green),
                contentColor = Color.White,
                disabledContentColor = Color.White,
                disabledContainerColor = colorResource(id = R.color.label_text_green).copy(alpha = 0.1f)
            ),
            contentPadding = PaddingValues(vertical = 16.dp),
            modifier = Modifier
                .padding(24.dp)
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            enabled = setContinueBtnStatus
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