@file:OptIn(ExperimentalMaterialApi::class)

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import org.exercise.remitconnectclaude.presentation.ui.SendMoneyToAfricaViewModel
import org.exercise.remitconnectclaude.presentation.ui.utils.enterTransition
import org.exercise.remitconnectclaude.presentation.ui.utils.exitTransition
import org.exercise.remitconnectclaude.presentation.ui.homeNavigation.TransactionScreen
import org.exercise.remitconnectclaude.presentation.ui.inputAmount.InputAmountToSendScreen
import org.exercise.remitconnectclaude.presentation.ui.utils.popEnterTransition
import org.exercise.remitconnectclaude.presentation.ui.utils.popExitTransition
import org.exercise.remitconnectclaude.presentation.ui.recipientDetails.RecipientDetailsScreen
import org.exercise.remitconnectclaude.presentation.ui.selectWallet.SelectMobileWalletScreen
import org.exercise.remitconnectclaude.presentation.ui.sendMoney.SelectMoneyToAfricaScreen
import org.exercise.remitconnectclaude.presentation.ui.sendMoney.SendMoneyScreen
import org.exercise.remitconnectclaude.presentation.ui.successfulTransaction.SuccessfulTransactionScreen

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeGraph(modifier: Modifier, navController: NavHostController) {
    val viewModel: SendMoneyToAfricaViewModel = hiltViewModel()

    AnimatedNavHost(
        navController = navController,
        route = Graph.HOME,
        modifier = modifier,
        startDestination = Destinations.TransactionScreen.route
    ) {
        composable(
            route = Destinations.TransactionScreen.route,
        ) {
            TransactionScreen(navController = navController)
        }

        composable(
            route = Destinations.SendMoneyScreen.route,
        ) {
            SendMoneyScreen(
                navigateBack = {
                    navController.navigate(route = Destinations.TransactionScreen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }

                },
                onSendMoneyTypeSelected = { itemId, name ->
                    navController.navigate(Destinations.SendToAfricaScreen.route + "/${name}" + "/${itemId}")
                })
        }

        sendToAfricaScreenNavGraph(navController = navController, navGraphBuilder = this, )
        recipientDetailsScreenNavGraph(navController = navController, navGraphBuilder = this, viewModel)
        selectMobileWalletProviderScreenNavGraph(
            navController = navController,
            navGraphBuilder = this,
            viewModel
        )
        inputAmountToSendScreenNavGraph(navController = navController, navGraphBuilder = this, viewModel)
        successfulTransactionScreenNavGraph(navController = navController, navGraphBuilder = this)
    }
}

@OptIn(ExperimentalAnimationApi::class)
private fun sendToAfricaScreenNavGraph(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(
        route = Destinations.SendToAfricaScreen.withArgsFormat(
            Destinations.SendToAfricaScreen.itemName,
            Destinations.SendToAfricaScreen.itemId
        ),
        arguments = listOf(
            navArgument(Destinations.SendToAfricaScreen.itemId) {
                type = NavType.IntType
            },
            navArgument(Destinations.SendToAfricaScreen.itemName) {
                type = NavType.StringType
            },
        ),

        enterTransition = {
            when (initialState.destination.route) {
                Destinations.SendToAfricaScreen.route -> enterTransition
                else -> null
            }
        },
        exitTransition = {
            when (targetState.destination.route) {
                Destinations.SendToAfricaScreen.route -> exitTransition
                else -> null
            }
        },
        popEnterTransition = {
            when (initialState.destination.route) {
                Destinations.SendMoneyScreen.route -> popEnterTransition
                Destinations.SendToAfricaScreen.route -> popEnterTransition
                else -> null
            }
        },
        popExitTransition = {
            when (targetState.destination.route) {
                Destinations.SendMoneyScreen.route -> popExitTransition
                Destinations.SendToAfricaScreen.route -> popExitTransition
                else -> null
            }
        },

        ) { navBackStackEntry ->
        val args = navBackStackEntry.arguments
        SelectMoneyToAfricaScreen(
            moneyTransferTypeId = args?.getInt(Destinations.SendToAfricaScreen.itemId) ?: -1,
            moneyTransferTypeName = args?.getString(Destinations.SendToAfricaScreen.itemName) ?: "",
            popBackStack = { navController.popBackStack() },
            onSendMoneySelected = {
                navController.navigate(Destinations.RecipientDetailsScreen.route)
            }
        )
    }
}

@ExperimentalMaterialApi
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
private fun recipientDetailsScreenNavGraph(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder,
    viewModel: SendMoneyToAfricaViewModel
) {
    navGraphBuilder.composable(
        route = Destinations.RecipientDetailsScreen.route,
        enterTransition = {
            when (initialState.destination.route) {
                Destinations.SendToAfricaScreen.route -> enterTransition
                Destinations.RecipientDetailsScreen.route -> enterTransition
                else -> null
            }
        },
        exitTransition = {
            when (targetState.destination.route) {
                Destinations.SendToAfricaScreen.route -> exitTransition
                Destinations.RecipientDetailsScreen.route -> exitTransition
                else -> null
            }
        },
        popExitTransition = {
            when (targetState.destination.route) {
                Destinations.SendToAfricaScreen.route -> popExitTransition
                Destinations.RecipientDetailsScreen.route -> popExitTransition
                else -> null
            }
        },

        ) { navBackStackEntry ->

        RecipientDetailsScreen(navController = navController, viewModel = viewModel)
    }
}

@ExperimentalMaterialApi
@OptIn(ExperimentalAnimationApi::class)
private fun selectMobileWalletProviderScreenNavGraph(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder,
    viewModel: SendMoneyToAfricaViewModel
) {
    navGraphBuilder.composable(
        route = Destinations.SelectMobileWalletScreen.route,
        enterTransition = {
            when (initialState.destination.route) {
                Destinations.RecipientDetailsScreen.route -> enterTransition
                Destinations.SelectMobileWalletScreen.route -> enterTransition
                else -> null
            }
        },
        exitTransition = {
            when (targetState.destination.route) {
                Destinations.RecipientDetailsScreen.route -> exitTransition
                Destinations.SelectMobileWalletScreen.route -> exitTransition
                else -> null
            }
        },
        popExitTransition = {
            when (targetState.destination.route) {
                Destinations.RecipientDetailsScreen.route -> popExitTransition
                Destinations.SelectMobileWalletScreen.route -> popExitTransition
                else -> null
            }
        },

        ) { navBackStackEntry ->

        SelectMobileWalletScreen(
            popBackStack = {
                navController.popBackStack()
            },
            onProviderSelected = {
                navController.navigate(Destinations.InputAmountToSendScreen.route)
            },
            sendMoneyToAfricaViewModel = viewModel)
    }
}


@ExperimentalMaterialApi
@OptIn(ExperimentalAnimationApi::class)
private fun inputAmountToSendScreenNavGraph(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder,
    viewModel: SendMoneyToAfricaViewModel
) {
    navGraphBuilder.composable(
        route = Destinations.InputAmountToSendScreen.route,
        enterTransition = {
            when (initialState.destination.route) {
                Destinations.SelectMobileWalletScreen.route -> enterTransition
                Destinations.InputAmountToSendScreen.route -> enterTransition
                else -> null
            }
        },
        exitTransition = {
            when (targetState.destination.route) {
                Destinations.SelectMobileWalletScreen.route -> exitTransition
                Destinations.InputAmountToSendScreen.route -> exitTransition
                else -> null
            }
        },
        popExitTransition = {
            when (targetState.destination.route) {
                Destinations.SelectMobileWalletScreen.route -> popExitTransition
                Destinations.InputAmountToSendScreen.route -> popExitTransition
                else -> null
            }
        },

        ) { navBackStackEntry ->

        InputAmountToSendScreen(navController, sendMoneyToAfricaViewModel = viewModel)
    }
}

@ExperimentalMaterialApi
@OptIn(ExperimentalAnimationApi::class)
private fun successfulTransactionScreenNavGraph(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder,
) {
    navGraphBuilder.composable(
        route = Destinations.SuccessfulTransactionScreen.route,
        enterTransition = {
            when (initialState.destination.route) {
                Destinations.InputAmountToSendScreen.route -> enterTransition
                Destinations.SuccessfulTransactionScreen.route -> enterTransition
                else -> null
            }
        },
        exitTransition = {
            when (targetState.destination.route) {
                Destinations.InputAmountToSendScreen.route -> exitTransition
                Destinations.SuccessfulTransactionScreen.route -> exitTransition
                else -> null
            }
        },
        popExitTransition = {
            when (targetState.destination.route) {
                Destinations.InputAmountToSendScreen.route -> popExitTransition
                Destinations.SuccessfulTransactionScreen.route -> exitTransition
                else -> null
            }
        },

        ) { navBackStackEntry ->

        SuccessfulTransactionScreen {
            navController.navigate(route = Destinations.TransactionScreen.route) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            }
        }
    }
}