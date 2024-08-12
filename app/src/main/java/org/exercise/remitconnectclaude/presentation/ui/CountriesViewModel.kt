package org.exercise.remitconnectclaude.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.exercise.remitconnectclaude.data.SupportedCountriesRepository
import org.exercise.remitconnectclaude.data.SupportedRecipientCountry
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(private val supportedCountriesRepository: SupportedCountriesRepository) :
    ViewModel() {

    private val _countriesFlow = MutableStateFlow((SupportedCountriesState()))
    val countriesFlow = _countriesFlow.asStateFlow()

    init {
        getSupportedCountries()
    }

    fun getSupportedCountries() = viewModelScope.launch(Dispatchers.Main) {
        _countriesFlow.update {
            it.copy(loading = true)
        }

        val response = withContext(Dispatchers.IO) {
            supportedCountriesRepository.getSupportedCountriesForRecipients()
        }
        _countriesFlow.update {
            it.copy(
                supportedCountries = response.data ?: emptyList(),
                throwable = response.throwable,
                loading = false
            )
        }
    }
}

data class SupportedCountriesState(
    val supportedCountries: List<SupportedRecipientCountry> = emptyList(),
    val throwable: Throwable? = null,
    val loading: Boolean = false
)