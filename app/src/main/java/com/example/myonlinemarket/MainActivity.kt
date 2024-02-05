package com.example.myonlinemarket

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myonlinemarket.navigation.BottomBar
import com.example.myonlinemarket.navigation.NavigationGraph
import com.example.myonlinemarket.navigation.TopBar
import com.example.myonlinemarket.screens.RegistrationScreen
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
            val userIsInDataBase = viewModel.userInDatabase.collectAsState()

            MyOnlineMarketTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    containerColor = MyOnlineMarketTheme.colors.primaryBackground,
                    topBar = {
                        TopBar()
                    },
                    bottomBar = {
                        if (userIsInDataBase.value){
                            BottomBar(
                                navController = navController,
                                state = true,
                                modifier = Modifier.height(70.dp)
                                    .clickable { Log.e("9849845",selectedProduct.value.toString()) }
                            )
                        }
                    }) { paddingValues ->
                    Box(
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        if (!userIsInDataBase.value){
                            RegistrationScreen()
                        }
                        NavigationGraph(navController = navController, viewModel)
                    }
                }
            }
        }
    }
}