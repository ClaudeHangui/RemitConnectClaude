package org.exercise.remitconnectclaude.presentation.ui.homeNavigation

import Destinations
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.exercise.remitconnectclaude.R

@Composable
fun BottomBarNav(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentDestination = navBackStackEntry?.destination
    val routesWithoutBottomBar = listOf(
        Destinations.SuccessfulTransactionScreen.route,
        Destinations.InputAmountToSendScreen.route,
        Destinations.SelectMobileWalletScreen.route,
        Destinations.SendMoneyScreen.route,
        Destinations.RecipientDetailsScreen.route,
        Destinations.SendToAfricaScreen.withArgsFormat(Destinations.SendToAfricaScreen.itemName, Destinations.SendToAfricaScreen.itemId)
    )

    //val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    val isItemFound = routesWithoutBottomBar.contains(currentDestination?.route)
    val isBottomBarVisible = !isItemFound

    if (isBottomBarVisible) {
        BottomNavigation(
            backgroundColor = Color.White,
            modifier = Modifier.height(104.dp)
        ) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

val screens = listOf(
    Destinations.TransactionScreen,
    Destinations.CardsScreen,
    Destinations.SendMoneyScreen,
    Destinations.TontinesScreen,
    Destinations.SettingsScreen,
)

@Composable
fun RowScope.AddItem(
    screen: Destinations,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val isTabSelected = currentDestination?.hierarchy?.any {
        it.route == screen.route
    } == true

    val selectedTabColor = if (isTabSelected) colorResource(id = R.color.green) else colorResource(
        id = R.color.small_label_light_gray
    )

    BottomNavigationItem(
        label = {
            screen.title?.let { Text(
                text = it,
                style = MaterialTheme.typography.labelMedium,
                color = selectedTabColor,
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                ) }
        },
        icon = {
            screen.icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = "Navigation Icon",
                    tint = selectedTabColor
                )
            }
        },
        modifier = Modifier.padding(bottom = 40.dp, top = 8.dp),
        selected = isTabSelected,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        selectedContentColor = colorResource(id = R.color.selected_tab_yellow),
        onClick = {
            try {
                navController.navigate(screen.route) {
                    /*
                    popUpTo(navController.graph.findStartDestination().id) {
                        inclusive = true
                        saveState = true
                    }
                    */
                    launchSingleTop = true
                    restoreState = true
                }

            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    )
}