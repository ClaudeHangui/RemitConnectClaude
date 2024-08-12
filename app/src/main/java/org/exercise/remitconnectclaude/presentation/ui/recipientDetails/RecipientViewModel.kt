package org.exercise.remitconnectclaude.presentation.ui.recipientDetails

import androidx.lifecycle.ViewModel
import com.google.i18n.phonenumbers.PhoneNumberUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RecipientViewModel @Inject constructor() : ViewModel() {

    private var phoneNumberUtil: PhoneNumberUtil = PhoneNumberUtil.getInstance()
    private val _validationState =
        MutableStateFlow<InputFormValidationState>(InputFormValidationState.None)
    val validationState = _validationState.asStateFlow()

    fun validateInput(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        dialingCode: String
    ) {
        val inputFormState = InputFormFailureValidationState().apply {
            isFirstNameFieldEmpty = firstName.isEmpty()
            isLastNameFieldEmpty = lastName.isEmpty()
            phoneFieldState = when {
                phoneNumber.trim().isEmpty() -> PhoneFieldState.PhoneFieldIsEmpty
                isPhoneNumberInValid(
                    phoneNumber.trim(),
                    dialingCode
                ) == null -> PhoneFieldState.PhoneFieldStartsWithNonPositiveDigit

                isPhoneNumberInValid(
                    phoneNumber.trim(),
                    dialingCode
                ) == true -> PhoneFieldState.PhoneFieldInvalid

                else -> PhoneFieldState.PhoneFieldValid
            }
        }

        _validationState.value = when {
            inputFormState.isFirstNameFieldEmpty == true ||
                    inputFormState.isLastNameFieldEmpty == true ||
                    inputFormState.phoneFieldState != PhoneFieldState.PhoneFieldValid ->
                InputFormValidationState.Failure(inputFormState)

            else -> InputFormValidationState.Success
        }
    }


    private fun isPhoneNumberInValid(phoneNumber: String, dialingCode: String): Boolean? {
        return if (phoneNumber.startsWith("+") || phoneNumber.startsWith("0")) {
            null
        } else !parsePhoneNumber(dialingCode + phoneNumber)
    }

    private fun parsePhoneNumber(phoneNumber: String) = try {
        val parsedPhoneNumber = phoneNumberUtil.parse(phoneNumber, null)
        val result = phoneNumberUtil.isValidNumber(parsedPhoneNumber)
        result
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }

    fun resetValidationState() {
        _validationState.value = InputFormValidationState.None
    }
}

data class InputFormFailureValidationState(
    var isFirstNameFieldEmpty: Boolean? = null,
    var isLastNameFieldEmpty: Boolean? = null,
    var phoneFieldState: PhoneFieldState? = null
)

sealed class InputFormValidationState {
    object Success : InputFormValidationState()
    object None : InputFormValidationState()
    data class Failure(val failure: InputFormFailureValidationState) : InputFormValidationState()
}


sealed class PhoneFieldState {
    object PhoneFieldIsEmpty : PhoneFieldState()
    object PhoneFieldInvalid : PhoneFieldState()
    object PhoneFieldStartsWithNonPositiveDigit : PhoneFieldState()
    object PhoneFieldValid : PhoneFieldState()
}