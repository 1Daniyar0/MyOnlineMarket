package com.example.myonlinemarket

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myonlinemarket.navigation.BottomBar
import com.example.myonlinemarket.navigation.Destinations
import com.example.myonlinemarket.navigation.NavigationGraph
import com.example.myonlinemarket.navigation.TopBar
import com.example.myonlinemarket.screens.registration_screen.RegistrationScreen
import com.example.myonlinemarket.ui.theme.MyOnlineMarketTheme
import com.example.myonlinemarket.viewModel.MarketViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MarketViewModel = koinViewModel()
            viewModel.checkUserInDateBase()
            val selectedProduct = viewModel.selectedProduct.collectAsState()

            val navController: NavHostController = rememberNavController()
            val userIsInDataBaseState = viewModel.userInDatabase.collectAsState()
            var userIsInDataBase by remember(userIsInDataBaseState.value) {
                mutableStateOf(userIsInDataBaseState.value)
            }

            MyOnlineMarketTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    containerColor = MyOnlineMarketTheme.colors.primaryBackground,
                    topBar = {
                        TopBar(viewModel,navController)
                    },
                    bottomBar = {
                        if (userIsInDataBase){
                            BottomBar(
                                navController = navController,
                                state = true,
                                modifier = Modifier
                                    .height(70.dp)
                                    .border(
                                        BorderStroke(
                                            1.dp,
                                            MyOnlineMarketTheme.colors.secondaryBackground
                                        )
                                    )
                            )
                        }
                    }) { paddingValues ->
                    Box(
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        NavigationGraph(
                            navController = navController,
                            viewModel = viewModel,
                            startDestination = if (!userIsInDataBase) Destinations.RegistrationScreen.route
                            else Destinations.CatalogScreen.route
                        )

                    }
                }
            }
        }
    }
}