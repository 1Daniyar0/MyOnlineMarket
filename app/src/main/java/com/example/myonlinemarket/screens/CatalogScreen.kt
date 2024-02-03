package com.example.myonlinemarket.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.models.ListProduct
import com.example.domain.models.Product
import com.example.myonlinemarket.R
import com.example.myonlinemarket.ui.theme.MyOnlineMarketTheme
import com.example.myonlinemarket.viewModel.MarketViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import org.koin.androidx.compose.koinViewModel
import kotlin.math.absoluteValue

@Composable
fun CatalogScreen(viewModel: MarketViewModel = koinViewModel()){
    viewModel.getListOfProduct()
    val listOfProduct = viewModel.listOfProduct.collectAsState()
    ProductCatalog(listOfProduct.value)
}

@Composable
fun ProductCatalog(list: ListProduct){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        content = {
            itemsIndexed(list.items){index, item ->
                ProductItem(item)

            }
        })
}

@Composable
fun ProductItem(product: Product){
    Card(
        shape = MyOnlineMarketTheme.shapes.cornersStyle,
        border = BorderStroke(1.dp, MyOnlineMarketTheme.colors.secondaryBackground),
        colors = CardDefaults.cardColors(
            containerColor = MyOnlineMarketTheme.colors.primaryBackground
        ),
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize()
    ) {
        ImageSlider()
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageSlider(){
    val pagerState = rememberPagerState(initialPage = 0)
    val imageSlider = listOf(
        painterResource(id = R.drawable.product_1),
        painterResource(id = R.drawable.product_3),
        painterResource(id = R.drawable.product_2)
    )
    Column {
        HorizontalPager(
            count = imageSlider.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
        ) { page ->
            Image(
                painter = imageSlider[page],
                contentDescription = "",
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
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
                .padding(vertical = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CatalogPreview() {
    MyOnlineMarketTheme {
        ImageSlider()

    }
}