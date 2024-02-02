package com.example.myonlinemarket.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myonlinemarket.screens.RegistrationScreen
import com.example.myonlinemarket.ui.theme.MyOnlineMarketTheme

@Composable
fun BottomBar(
    navController: NavHostController, state: MutableState<Boolean>, modifier: Modifier
) {
    val screens = listOf(
        Destinations.HomeScreen,
        Destinations.CatalogScreen,
        Destinations.BasketScreen,
        Destinations.SaleScreen,
        Destinations.ProfileScreen,
    )

    NavigationBar(
        modifier = modifier,
        containerColor = MyOnlineMarketTheme.colors.primaryBackground,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { screen ->

            NavigationBarItem(
                label = {
                    Text(
                        text = screen.title!!,
                        style = MyOnlineMarketTheme.typography.firstCaption,
                        textAlign = TextAlign.Center
                    )
                },
                icon = {
                    Icon(imageVector = ImageVector.vectorResource(id = screen.icon!!), contentDescription = "")
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedTextColor = MyOnlineMarketTheme.colors.accentColor,
                    unselectedTextColor = MyOnlineMarketTheme.colors.forthText,
                    selectedIconColor = MyOnlineMarketTheme.colors.accentColor,
                    unselectedIconColor = MyOnlineMarketTheme.colors.forthText,
                    indicatorColor = Color.White
                ),
            )
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Destinations.HomeScreen.route) {
        composable(Destinations.HomeScreen.route) {
            RegistrationScreen()
        }
        composable(Destinations.BasketScreen.route) {
            RegistrationScreen()
        }
        composable(Destinations.ProfileScreen.route) {
            RegistrationScreen()
        }
        composable(Destinations.CatalogScreen.route) {
            RegistrationScreen()
        }
        composable(Destinations.SaleScreen.route) {

        }

    }
}


