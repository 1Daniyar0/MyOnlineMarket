package com.example.myonlinemarket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myonlinemarket.screens.RegistrationScreen
import com.example.myonlinemarket.ui.theme.MyOnlineMarketTheme
import com.example.myonlinemarket.viewModel.MarketViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyOnlineMarketTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(MyOnlineMarketTheme.shapes.paddingBig),
                    color = MyOnlineMarketTheme.colors.primaryBackground
                ) {

                    RegistrationScreen()
                }
            }
        }
    }
}