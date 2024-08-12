package org.exercise.remitconnectclaude.presentation.ui.selectWallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.exercise.remitconnectclaude.data.MobileWalletRepository
import org.exercise.remitconnectclaude.presentation.ui.utils.ResourceHelper
import javax.inject.Inject

@HiltViewModel
class MobileWalletViewModel @Inject constructor(
    private val mobileWalletRepository: MobileWalletRepository,
    private val resourceHelper: ResourceHelper
) : ViewModel() {

    private val _mobileWalletFlow = MutableStateFlow(MobileWalletState())
    val mobileWalletFlow = _mobileWalletFlow.asStateFlow()

    init {
        getAllWallets()
    }

    fun getAllWallets() = viewModelScope.launch(Dispatchers.Main) {
        _mobileWalletFlow.update {
            it.copy(loading = true)
        }
        val response = withContext(Dispatchers.IO) {
            mobileWalletRepository.getSupportedMobileWallets()
        }
        _mobileWalletFlow.update {
            it.copy(
                loading = false,
                throwable = response.throwable,
                providers = response.data?.map { wallet ->
                    val logo = resourceHelper.getLogo(wallet.name)
                    MobileMoneyProvider(wallet.id, wallet.name, logo)
                } ?: emptyList(),
            )
        }
    }

    fun onProviderSelected(id: String, name: String) = viewModelScope.launch {
        _mobileWalletFlow.update {
            it.copy(
                selectedProviderId = id,
                providerName = name
            )
        }
    }

    fun hasMobileProviderBeenSelected(
        mobileMoneyProviderId: String,
        mobileMoneyProviderName: String
    ) = viewModelScope.launch {
        _mobileWalletFlow.update {
            it.copy(
                isProviderSelected = mobileMoneyProviderId.isNotEmpty() && mobileMoneyProviderName.isNotEmpty()
            )
        }
    }

    fun resetState() {
        _mobileWalletFlow.update {
            it.copy(isProviderSelected = false)
        }
    }
}

data class MobileWalletState(
    val providers : List<MobileMoneyProvider> = emptyList(),
    val isProviderSelected: Boolean = false,
    val selectedProviderId: String = "",
    val providerName: String = "",
    val loading: Boolean = false,
    val throwable: Throwable? = null,
    )