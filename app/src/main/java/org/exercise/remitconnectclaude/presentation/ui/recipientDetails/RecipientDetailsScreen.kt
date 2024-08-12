@file:OptIn(ExperimentalPermissionsApi::class)

package org.exercise.remitconnectclaude.presentation.ui.recipientDetails

import Destinations
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import org.exercise.remitconnectclaude.R
import org.exercise.remitconnectclaude.presentation.ui.AddRecipientScreen
import org.exercise.remitconnectclaude.presentation.ui.RecipientTopSection
import org.exercise.remitconnectclaude.presentation.ui.SendMoneyToAfricaViewModel
import org.exercise.remitconnectclaude.presentation.ui.utils.FailureState
import org.exercise.remitconnectclaude.presentation.ui.utils.ShowLoader

@ExperimentalMaterialApi
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun RecipientDetailsScreen(
    navController: NavController,
    recipientViewModel: RecipientViewModel = hiltViewModel(),
    phoneContactViewModel: PreviousRecipientViewModel = hiltViewModel(),
    viewModel: SendMoneyToAfricaViewModel,
) {

    val phoneContactsTabState =
        phoneContactViewModel.phoneContactsStateFlow.collectAsStateWithLifecycle()

    var query by remember { mutableStateOf("") }
    var currentTab by rememberSaveable {
        mutableIntStateOf(0)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        RecipientTopSection(
            currentTab,
            popBackStack = { navController.popBackStack() },
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
            onTabChange = { tab ->
                query = ""
                currentTab = tab
            }
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            if (currentTab == 0) {
                phoneContactsTabState.value.apply {
                    if (loading) {
                        ShowLoader()
                    }

                    androidx.compose.animation.AnimatedVisibility(visible = throwable != null && phoneContacts.isEmpty()) {
                        Box {
                            FailureState(
                                customErrorMsg = R.string.turn_online_to_get_wallets,
                                throwable = throwable, data = phoneContacts
                            ) {
                                phoneContactViewModel.getPhoneContacts()
                            }
                        }
                    }

                    androidx.compose.animation.AnimatedVisibility(visible = phoneContacts.isNotEmpty()) {
                        PreviousRecipientScreen(
                            modifier = Modifier.padding(top = 12.dp),
                            query = query,
                            phoneContacts = phoneContacts,
                            onQueryChange = { newQuery ->
                                query = newQuery
                            },
                        ) { recipient ->
                            query = ""
                            recipient.apply {
                                viewModel.updateFirstName(firstName)
                                viewModel.updateLastName(lastName)
                                viewModel.updateCountrySelected(country)
                                viewModel.updatePhoneNumber(phoneNumber)
                            }

                            viewModel.updatePhoneDialingCode("")
                            currentTab = 1
                        }
                    }
                }
            } else {
                AddRecipientScreen(
                    modifier = Modifier.padding(
                        top = 4.dp,
                        bottom = 24.dp
                    ),
                    onFormValid = { firstName, lastName, dialingCode, phone, countryName ->
                        viewModel.updateLastName(lastName)
                        viewModel.updateFirstName(firstName)
                        viewModel.updatePhoneNumber(phone)
                        viewModel.updatePhoneDialingCode(dialingCode)
                        viewModel.updateCountrySelected(countryName)
                        navController.navigate(Destinations.SelectMobileWalletScreen.route)
                    },
                    onChangeTab = { position ->
                        currentTab = position
                    },
                    recipientViewModel = recipientViewModel,
                    sendMoneyToAfricaViewModel = viewModel
                )
            }
        }
    }
}


