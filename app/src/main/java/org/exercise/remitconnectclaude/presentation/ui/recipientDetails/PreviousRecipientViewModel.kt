package org.exercise.remitconnectclaude.presentation.ui.recipientDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.exercise.remitconnectclaude.data.Recipient
import org.exercise.remitconnectclaude.data.RecipientRepository
import javax.inject.Inject

@HiltViewModel
class PreviousRecipientViewModel @Inject constructor(
    private val recipientRepository: RecipientRepository): ViewModel() {

    private val _phoneContactsStateFlow = MutableStateFlow(PhoneContactsState())
    val phoneContactsStateFlow = _phoneContactsStateFlow.asStateFlow()

    init {
        getPhoneContacts()
    }

    fun getPhoneContacts() = viewModelScope.launch(Dispatchers.Main) {
        _phoneContactsStateFlow.update {
            it.copy(loading = true)
        }

        val response = withContext(Dispatchers.IO) { recipientRepository.getAllPreviousRecipients() }

        _phoneContactsStateFlow.update {
            it.copy(
                loading = false,
                throwable = response.throwable,
                phoneContacts = response.data ?: emptyList()
            )
        }
    }
}

data class PhoneContactsState(
    val phoneContacts: List<Recipient> = emptyList(),
    val loading: Boolean = false,
    val throwable: Throwable? = null,
    )