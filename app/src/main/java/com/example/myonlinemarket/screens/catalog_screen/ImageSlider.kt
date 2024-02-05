package com.example.myonlinemarket.screens.catalog_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.domain.models.Product
import com.example.myonlinemarket.R
import com.example.myonlinemarket.ui.theme.MyOnlineMarketTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageSlider(product: Product){
    val pagerState = rememberPagerState(initialPage = 0)
    val imageSlider = listOf(
        when(product.id){
            "cbf0c984-7c6c-4ada-82da-e29dc698bb50" ->{
                painterResource(id = R.drawable.product_6)
                painterResource(id = R.drawable.product_5)
            }
            "54a876a5-2205-48ba-9498-cfecff4baa6e" ->{
                painterResource(id = R.drawable.product_1)
                painterResource(id = R.drawable.product_2)
            }
            "75c84407-52e1-4cce-a73a-ff2d3ac031b3" ->{
                painterResource(id = R.drawable.product_5)
                painterResource(id = R.drawable.product_6)
            }
            "16f88865-ae74-4b7c-9d85-b68334bb97db" ->{
                painterResource(id = R.drawable.product_3)
                painterResource(id = R.drawable.product_4)
            }
            "26f88856-ae74-4b7c-9d85-b68334bb97db" ->{
                painterResource(id = R.drawable.product_2)
                painterResource(id = R.drawable.product_3)
            }
            "15f88865-ae74-4b7c-9d81-b78334bb97db" ->{
                painterResource(id = R.drawable.product_6)
                painterResource(id = R.drawable.product_1)
            }
            "88f88865-ae74-4b7c-9d81-b78334bb97db" ->{
                painterResource(id = R.drawable.product_4)
                painterResource(id = R.drawable.product_3)
            }
            "55f58865-ae74-4b7c-9d81-b78334bb97db" ->{
                painterResource(id = R.drawable.product_1)
                painterResource(id = R.drawable.product_5)
            }
            else -> painterResource(id = R.drawable.product_1)
        }
    )
    Column {
        HorizontalPager(
            count = imageSlider.size,
            state = pagerState,
            modifier = Modifier
                .wrapContentSize()
                .height(130.dp)
        ) { page ->
            Image(
                painter = imageSlider[page],
                contentDescription = "",
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center,
                modifier = Modifier
                    .wrapContentSize()
                    .background(Color.White)
            )
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            activeColor = MyOnlineMarketTheme.colors.accentColor,
            inactiveColor = MyOnlineMarketTheme.colors.secondaryBackground,
            indicatorHeight = 4.dp,
            indicatorWidth = 4.dp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
    }
}
