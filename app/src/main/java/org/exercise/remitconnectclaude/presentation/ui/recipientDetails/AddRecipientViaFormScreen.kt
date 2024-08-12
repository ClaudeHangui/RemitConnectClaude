@file:OptIn(ExperimentalMaterial3Api::class)

package org.exercise.remitconnectclaude.presentation.ui.recipientDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import org.exercise.remitconnectclaude.R
import org.exercise.remitconnectclaude.presentation.ui.Form

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddRecipientViaFormScreen(
    phoneContact: Form,
    inputFormValidationState: InputFormValidationState,
    modifier: Modifier = Modifier,
    onPhoneNumberChange: (String) -> Unit,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
) {

    var phoneNumber by remember { mutableStateOf(TextFieldValue(phoneContact.phoneNumber)) }
    var firstName by remember { mutableStateOf(TextFieldValue(phoneContact.firstName)) }
    var lastName by remember { mutableStateOf(TextFieldValue(phoneContact.lastName)) }

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    var isPhoneInvalid = inputFormValidationState is InputFormValidationState.Failure &&
            (inputFormValidationState.failure.phoneFieldState != PhoneFieldState.PhoneFieldValid)

    var isFirstNameInvalid =
        inputFormValidationState is InputFormValidationState.Failure &&
                inputFormValidationState.failure.isFirstNameFieldEmpty == true

    var isLastNameInvalid =
        inputFormValidationState is InputFormValidationState.Failure &&
                inputFormValidationState.failure.isLastNameFieldEmpty == true

    Column(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
    ) {

        Text(
            text = stringResource(id = R.string.phone_number_label),
            color = colorResource(id = R.color.dark_blue),
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            value = phoneNumber,
            textStyle = MaterialTheme.typography.titleMedium.copy(
                color = colorResource(id = R.color.dark_blue)
            ),
            onValueChange = {
                phoneNumber = it
                isPhoneInvalid = false
                onPhoneNumberChange.invoke(phoneNumber.text)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done
            ),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = colorResource(id = R.color.border_gray),
                focusedBorderColor = colorResource(id = R.color.border_gray),
                focusedLabelColor = colorResource(id = R.color.border_gray),
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                cursorColor = colorResource(id = R.color.small_label_light_gray),
            ),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            placeholder = {
                Text(
                    text = "Enter phone number",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Normal,
                    color = colorResource(id = R.color.small_label_light_gray)
                )
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    focusRequester.freeFocus()
                    keyboardController?.hide()
                }
            ),
            isError = isPhoneInvalid,
            supportingText = {
                if (isPhoneInvalid) {
                    val error =
                        if (inputFormValidationState is InputFormValidationState.Failure &&
                            inputFormValidationState.failure.phoneFieldState == PhoneFieldState.PhoneFieldIsEmpty
                        ) {
                            R.string.error_phone_empty
                        } else if (inputFormValidationState is InputFormValidationState.Failure &&
                            inputFormValidationState.failure.phoneFieldState == PhoneFieldState.PhoneFieldStartsWithNonPositiveDigit
                        ) {
                            R.string.error_phone_starts_invalid_character
                        } else {
                            R.string.error_phone_invalid
                        }
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = error),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            trailingIcon = {
                if (isPhoneInvalid) {
                    ErrorIcon()
                }
            },
            readOnly = false
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.first_name_label),
            color = colorResource(id = R.color.dark_blue),
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            value = firstName,
            textStyle = MaterialTheme.typography.titleMedium.copy(
                color = colorResource(id = R.color.dark_blue)
            ),
            onValueChange = {
                firstName = it
                isFirstNameInvalid = false
                onFirstNameChange.invoke(firstName.text)
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = colorResource(id = R.color.border_gray),
                focusedBorderColor = colorResource(id = R.color.border_gray),
                focusedLabelColor = colorResource(id = R.color.border_gray),
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                cursorColor = colorResource(id = R.color.small_label_light_gray),
            ),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            placeholder = {
                Text(
                    text = "Enter first name",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Normal,
                    color = colorResource(id = R.color.small_label_light_gray)
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),

            keyboardActions = KeyboardActions(
                onDone = {
                    focusRequester.freeFocus()
                    keyboardController?.hide()
                }
            ),
            isError = isFirstNameInvalid,
            supportingText = {
                if (isFirstNameInvalid) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.error_firstname_empty),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            trailingIcon = {
                if (isFirstNameInvalid) {
                    ErrorIcon()
                }
            },
            readOnly = false

        )


        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.last_name_label),
            color = colorResource(id = R.color.dark_blue),
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            value = lastName,
            textStyle = MaterialTheme.typography.titleMedium.copy(
                color = colorResource(id = R.color.dark_blue)
            ),
            onValueChange = {
                lastName = it
                isLastNameInvalid = false
                onLastNameChange.invoke(lastName.text)
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = colorResource(id = R.color.border_gray),
                focusedBorderColor = colorResource(id = R.color.border_gray),
                focusedLabelColor = colorResource(id = R.color.border_gray),
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                cursorColor = colorResource(id = R.color.small_label_light_gray),
            ),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            placeholder = {
                Text(
                    text = "Enter last name",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Normal,
                    color = colorResource(id = R.color.small_label_light_gray)
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),

            keyboardActions = KeyboardActions(
                onDone = {
                    focusRequester.freeFocus()
                    keyboardController?.hide()
                },

                ),
            isError = isLastNameInvalid,
            supportingText = {
                if (isLastNameInvalid) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.error_last_name_empty),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            trailingIcon = {
                if (isLastNameInvalid) {
                    ErrorIcon()
                }
            },
            readOnly = false
        )

        Spacer(modifier = Modifier.height(24.dp))

    }
}

@Composable
fun ErrorIcon() {
    Icon(
        painter = painterResource(id = R.drawable.ic_error_red),
        contentDescription = "error",
        tint = MaterialTheme.colorScheme.error,
        modifier = Modifier.size(24.dp)
    )
}