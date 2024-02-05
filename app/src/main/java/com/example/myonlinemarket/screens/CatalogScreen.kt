package com.example.myonlinemarket.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.models.Product
import com.example.myonlinemarket.R
import com.example.myonlinemarket.ui.theme.MyOnlineMarketTheme
import com.example.myonlinemarket.viewModel.MarketViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import org.koin.androidx.compose.koinViewModel

enum class SortingOption {
    POPULARITY, PRICE_LOW_TO_HIGH, PRICE_HIGH_TO_LOW
}

@SuppressLint("MutableCollectionMutableState")
@Composable
fun CatalogScreen(viewModel: MarketViewModel = koinViewModel()){
    viewModel.getListOfProduct()
    val listOfProduct = viewModel.listOfProduct.collectAsState()
    var sortingOption by remember { mutableStateOf(SortingOption.POPULARITY) }
    var tagsOption by remember { mutableStateOf("All") }
    Column(
        Modifier.padding(horizontal = MyOnlineMarketTheme.shapes.paddingBig)
    ) {
        ProductFilter(){
            sortingOption = it
        }
        TagFilter(){
            tagsOption = it
        }
        ProductCatalog(listOfProduct.value.items, sortingOption, tagsOption)
    }
}
@Composable
fun TagFilter(onTagClick: (String) -> Unit){
    var selectedItemId by remember { mutableStateOf<Int>(0) }
    val tagsName = listOf( "Смотреть все", "Лицо", "Тело", "Загар", "Маски")
    val tags = listOf( "All", "face", "body", "suntan", "mask")
    LazyRow(modifier = Modifier.padding(bottom = 32.dp),
        content = {
        itemsIndexed(tagsName){index,item  ->
            var itemSelected by remember(selectedItemId) { mutableStateOf(selectedItemId == index) }
            Card(
                shape = RoundedCornerShape(100.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if(itemSelected) MyOnlineMarketTheme.colors.selectBackground
                    else MyOnlineMarketTheme.colors.secondaryBackground
                ),
                modifier = Modifier
                    .padding(end = MyOnlineMarketTheme.shapes.paddingMini)
                    .clickable {
                        selectedItemId = index
                        onTagClick(tags[index])
                    }
            ) {
                Row {
                    Text(text = item,
                        style = if(itemSelected) MyOnlineMarketTheme.typography.forthTitle
                        else MyOnlineMarketTheme.typography.secondButtonText,
                        color = if(itemSelected) Color.White
                        else MyOnlineMarketTheme.colors.secondaryText,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(horizontal = 12.dp, vertical = 5.dp))
                    if(itemSelected){
                        Image(painter = painterResource(id = R.drawable.white_x),
                            contentDescription = "",
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(top = 10.dp, bottom = 10.dp, end = 10.dp)
                                .clickable { selectedItemId = 0 })
                    }
                }
            }
        }
    })
}
@Composable
fun ProductFilter(onItemClick:(SortingOption) -> Unit){
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var text by remember {
        mutableStateOf("По популярности")
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MyOnlineMarketTheme.shapes.paddingBig)
    ){
        Row(
            Modifier.clickable { isExpanded = true }
        ){
            Image(painter = painterResource(id = R.drawable.sort_filter),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.CenterVertically))
            Text(text = text,
                style = MyOnlineMarketTheme.typography.forthTitle,
                color = MyOnlineMarketTheme.colors.thirdText,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 6.dp))
            Image(painter = painterResource(id = R.drawable.down_arrow), contentDescription = "")


            DropdownMenu(expanded = isExpanded,
                onDismissRequest = {isExpanded = false},
                Modifier.background(MyOnlineMarketTheme.colors.primaryBackground)) {
                DropdownMenuItem(
                    text = {
                        Text(text = stringResource(R.string.sort_rating),
                            style = MyOnlineMarketTheme.typography.forthTitle,
                            color = MyOnlineMarketTheme.colors.thirdText,)
                    },
                    onClick = {
                        isExpanded = !isExpanded
                        onItemClick(SortingOption.POPULARITY)
                        text = "По популярности"
                    }

                )
                DropdownMenuItem(
                    text = {
                        Text(text = stringResource(R.string.sort_price_high),
                            style = MyOnlineMarketTheme.typography.forthTitle,
                            color = MyOnlineMarketTheme.colors.thirdText)
                    },
                    onClick = {
                        isExpanded = !isExpanded
                        onItemClick(SortingOption.PRICE_LOW_TO_HIGH)
                        text = "По возрастанию цены"

                    }
                )
                DropdownMenuItem(
                    text = {
                        Text(text = stringResource(R.string.sort_price_low),
                            style = MyOnlineMarketTheme.typography.forthTitle,
                            color = MyOnlineMarketTheme.colors.thirdText)
                    },
                    onClick = {
                        isExpanded = !isExpanded
                        onItemClick(SortingOption.PRICE_HIGH_TO_LOW)
                        text = "По убыванию цены"
                    }
                )
            }
        }
        Row(
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Image(painter = painterResource(id = R.drawable.filter),
                contentDescription ="",
                modifier = Modifier
                    .align(Alignment.CenterVertically))
            Text(text = stringResource(R.string.filters),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp))
        }
        
    }
}
@Composable
fun ProductCatalog(
    products: ArrayList<Product>,
    sortingOption: SortingOption,
    tagOption: String){
    val sortedProducts = remember(products,sortingOption,tagOption) {
        when(sortingOption){
            SortingOption.POPULARITY -> products.filter { it.tags.contains(tagOption) || tagOption == "All" }
                .sortedWith(compareBy { it.feedback?.rating }).reversed()
            SortingOption.PRICE_HIGH_TO_LOW -> products.filter { it.tags.contains(tagOption) || tagOption == "All" }
                .sortedWith(compareBy { it.price?.priceWithDiscount?.toLong() }).reversed()
            SortingOption.PRICE_LOW_TO_HIGH -> products.filter { it.tags.contains(tagOption) || tagOption == "All" }
                .sortedWith(compareBy { it.price?.priceWithDiscount?.toLong() })
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        content = {
            itemsIndexed(sortedProducts){index, item ->
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
            .wrapContentSize()
    ) {
        Box(
            modifier = Modifier.height(144.dp)
        ){
            ImageSlider(product)
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
                text = product.price?.price!!,
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
                text = product.price?.priceWithDiscount + " " + product.price?.unit,
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
                    text = "-"+ product.price?.discount + " %",
                    style = MyOnlineMarketTheme.typography.elementText,
                    color = Color.White,
                    modifier = Modifier
                        .padding(horizontal = MyOnlineMarketTheme.shapes.paddingMini)
                        .align(Alignment.CenterHorizontally))
            }
        }
        Text(
            text = product.title!!,
            style = MyOnlineMarketTheme.typography.thirdTitle,
            color = MyOnlineMarketTheme.colors.primaryText,
            modifier = Modifier.padding(start = MyOnlineMarketTheme.shapes.paddingMini))
        Text(
            text = product.subtitle!!,
            style = MyOnlineMarketTheme.typography.firstCaption,
            color = MyOnlineMarketTheme.colors.thirdText,
            modifier = Modifier
                .padding(start = MyOnlineMarketTheme.shapes.paddingMini)
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
                text = product.feedback?.rating.toString(),
                style = MyOnlineMarketTheme.typography.elementText,
                color = MyOnlineMarketTheme.colors.orangeColor,
                modifier = Modifier.padding(horizontal = 3.dp))
            Text(
                text = "(" + product.feedback?.count.toString() + ")",
                style = MyOnlineMarketTheme.typography.elementText,
                color = MyOnlineMarketTheme.colors.secondaryText)
        }
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.plust_button),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(1.dp))
        }

    }
}

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

@Preview(showBackground = true)
@Composable
fun CatalogPreview() {
    MyOnlineMarketTheme {
        ProductFilter(){

        }
    }
}