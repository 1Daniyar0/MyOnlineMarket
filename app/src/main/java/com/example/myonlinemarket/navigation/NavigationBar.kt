package com.example.myonlinemarket.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myonlinemarket.screens.product_page_screen.ProductPage
import com.example.myonlinemarket.screens.catalog_screen.CatalogScreen
import com.example.myonlinemarket.screens.empty_screens.BasketScreen
import com.example.myonlinemarket.screens.empty_screens.HomeScreen
import com.example.myonlinemarket.screens.empty_screens.SaleScreen
import com.example.myonlinemarket.screens.favorites_screen.FavoritesScreen
import com.example.myonlinemarket.screens.profile_screen.ProfileScreen
import com.example.myonlinemarket.screens.registration_screen.RegistrationScreen
import com.example.myonlinemarket.ui.theme.MyOnlineMarketTheme
import com.example.myonlinemarket.viewModel.MarketViewModel

@Composable
fun BottomBar(
    navController: NavHostController, state: Boolean, modifier: Modifier
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
                    unselectedTextColor = MyOnlineMarketTheme.colors.thirdText,
                    selectedIconColor = MyOnlineMarketTheme.colors.accentColor,
                    unselectedIconColor = MyOnlineMarketTheme.colors.thirdText,
                    indicatorColor = Color.White
                ),
            )
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController, viewModel: MarketViewModel) {
    NavHost(navController, startDestination = Destinations.HomeScreen.route) {
        composable(Destinations.HomeScreen.route) {
            viewModel.addTopBarText(Destinations.HomeScreen.title)
            HomeScreen()
        }
        composable(Destinations.BasketScreen.route) {
            viewModel.addTopBarText(Destinations.BasketScreen.title)
            BasketScreen()
        }
        composable(Destinations.ProfileScreen.route) {
            viewModel.addTopBarText(Destinations.ProfileScreen.title)
            ProfileScreen(navController,viewModel)
        }
        composable(Destinations.CatalogScreen.route) {
            viewModel.addTopBarText(Destinations.CatalogScreen.title)
            CatalogScreen(navController,viewModel)
        }
        composable(Destinations.SaleScreen.route) {
            viewModel.addTopBarText(Destinations.SaleScreen.title)
            SaleScreen()
        }
        composable(Destinations.RegistrationScreen.route) {
            viewModel.addTopBarText(Destinations.RegistrationScreen.title)
            viewModel.checkUserInDateBase()
            RegistrationScreen(navController,viewModel)
        }
        composable(Destinations.ProductPageScreen.route) {
            viewModel.addTopBarText(Destinations.ProductPageScreen.title)
            ProductPage(viewModel)
        }
        composable(Destinations.FavoritesScreen.route) {
            viewModel.addTopBarText(Destinations.FavoritesScreen.title)
            FavoritesScreen(viewModel,navController)
        }

    }
}


