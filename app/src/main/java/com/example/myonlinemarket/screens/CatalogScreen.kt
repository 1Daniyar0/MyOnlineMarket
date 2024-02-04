package com.example.myonlinemarket.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        Box(
            modifier = Modifier.height(144.dp)
        ){
            ImageSlider()
            Image(painter = painterResource(id = R.drawable.favorite_empty),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(36.dp)
                    .padding(6.dp))
        }
        Box(
            modifier = Modifier
                .height(12.dp)
                .width(24.dp)
        ){
            Text(
                text = product.price.price,
                style = MyOnlineMarketTheme.typography.elementText,
                color = MyOnlineMarketTheme.colors.secondaryText,
                modifier = Modifier
                    .padding(start = MyOnlineMarketTheme.shapes.paddingMini)
                    .align(Alignment.Center))
            Image(
                painter = painterResource(id = R.drawable.line),
                contentDescription = "",
                modifier = Modifier
                    .padding(start = MyOnlineMarketTheme.shapes.paddingMini)
                    .align(Alignment.Center))
        }
        Row{
            Text(
                text = product.price.priceWithDiscount + " " + product.price.unit,
                style = MyOnlineMarketTheme.typography.secondTitle,
                color = MyOnlineMarketTheme.colors.primaryText,
                modifier = Modifier
                    .padding(start = MyOnlineMarketTheme.shapes.paddingMini)
                    .align(Alignment.CenterVertically))
            Card(
                shape = RoundedCornerShape(4.dp),
                colors = CardDefaults.cardColors(MyOnlineMarketTheme.colors.accentColor),
                modifier = Modifier
                    .padding(start = MyOnlineMarketTheme.shapes.paddingSmall)
                    .height(16.dp)
                    .align(Alignment.CenterVertically)
            ){
                Text(
                    text = "-"+ product.price.discount + " %",
                    style = MyOnlineMarketTheme.typography.elementText,
                    color = Color.White,
                    modifier = Modifier
                        .padding(horizontal = MyOnlineMarketTheme.shapes.paddingMini)
                        .align(Alignment.CenterHorizontally))
            }
        }
        Text(
            text = product.title,
            style = MyOnlineMarketTheme.typography.thirdTitle,
            color = MyOnlineMarketTheme.colors.primaryText,
            modifier = Modifier.padding(start = MyOnlineMarketTheme.shapes.paddingMini))
        Text(
            text = product.subtitle,
            style = MyOnlineMarketTheme.typography.firstCaption,
            color = MyOnlineMarketTheme.colors.thirdText,
            modifier = Modifier.padding(start = MyOnlineMarketTheme.shapes.paddingMini)
                .height(37.dp))
        Row(
            modifier = Modifier.padding(start = MyOnlineMarketTheme.shapes.paddingMini)
        ){
            Image(painter = painterResource(id = R.drawable.rating_star),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(13.dp))
            Text(
                text = product.feedback.rating.toString(),
                style = MyOnlineMarketTheme.typography.elementText,
                color = MyOnlineMarketTheme.colors.orangeColor,
                modifier = Modifier.padding(horizontal = 3.dp))
            Text(
                text = "(" + product.feedback.count.toString() + ")",
                style = MyOnlineMarketTheme.typography.elementText,
                color = MyOnlineMarketTheme.colors.secondaryText)
        }
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.plust_button),
                contentDescription = "",
                modifier = Modifier.align(Alignment.CenterEnd)
                    .padding(1.dp))
        }

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
                .height(130.dp)
        ) { page ->
            Image(
                painter = imageSlider[page],
                contentDescription = "",
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
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

@Preview(showBackground = true)
@Composable
fun CatalogPreview() {
    MyOnlineMarketTheme {
        ImageSlider()

    }
}