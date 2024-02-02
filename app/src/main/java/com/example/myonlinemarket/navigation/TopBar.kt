package com.example.myonlinemarket.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myonlinemarket.ui.theme.MyOnlineMarketTheme
import com.example.myonlinemarket.viewModel.MarketViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TopBar(viewModel: MarketViewModel = koinViewModel()){
    val currentScreen = viewModel.currentScreen.collectAsState()
    Text(
        text = currentScreen.value,
        style = MyOnlineMarketTheme.typography.firstTitle,
        color = MyOnlineMarketTheme.colors.primaryText,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(vertical = 20.dp)
            .fillMaxWidth())
}