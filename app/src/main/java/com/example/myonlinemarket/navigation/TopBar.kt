package com.example.myonlinemarket.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myonlinemarket.R
import com.example.myonlinemarket.ui.theme.MyOnlineMarketTheme
import com.example.myonlinemarket.viewModel.MarketViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TopBar(viewModel: MarketViewModel,navController: NavHostController){
    val currentScreen = viewModel.currentScreen.collectAsState()
    if (currentScreen.value == "product_page"){
        Box(
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight()
        ){
            Image(
                painter = painterResource(id = R.drawable.arrow_left),
                contentDescription = "",
                modifier = Modifier.align(Alignment.CenterStart)
                    .padding(top = 14.dp, start = 21.dp)
                    .clickable { navController.popBackStack() })
            Image(
                painter = painterResource(id = R.drawable.share),
                contentDescription = "",
                modifier = Modifier.align(Alignment.CenterEnd)
                    .padding(top = 18.dp, end = 19.dp, bottom = 11.dp))
        }
    }
    else if (currentScreen.value == "Избранное"){
        Row(
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight()
        ){
            Image(
                painter = painterResource(id = R.drawable.arrow_left),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(top = 24.dp, start = 21.dp, bottom = 14.dp)
                    .clickable { navController.popBackStack() })
            Text(
                text = currentScreen.value,
                style = MyOnlineMarketTheme.typography.firstTitle,
                color = MyOnlineMarketTheme.colors.primaryText,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 9.dp, start = 28.dp)
                    .align(Alignment.CenterVertically))
        }
    }
    else{
        Text(
            text = currentScreen.value,
            style = MyOnlineMarketTheme.typography.firstTitle,
            color = MyOnlineMarketTheme.colors.primaryText,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(vertical = 20.dp)
                .fillMaxWidth())
    }

}