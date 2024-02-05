package com.example.myonlinemarket.screens.product_page_screen

import android.icu.text.PluralRules
import android.icu.util.ULocale
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myonlinemarket.R
import com.example.myonlinemarket.screens.catalog_screen.ImageSlider
import com.example.myonlinemarket.ui.theme.MyOnlineMarketTheme
import com.example.myonlinemarket.viewModel.MarketViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductPage(viewModel: MarketViewModel){
    val productState = viewModel.selectedProduct.collectAsState()
    viewModel.getIdFavoriteProductDb()
    val isFavoritesState = viewModel.listOfIdFavorites.collectAsState()
    val isFavorite by remember(isFavoritesState.value) {
        mutableStateOf(isFavoritesState.value.contains(productState.value.id))
    }
    val rating by remember {
        mutableStateOf(productState.value.feedback?.rating)
    }
    var showDescription by remember {
        mutableStateOf(false)
    }
    var showCompound by remember {
        mutableStateOf(false)
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .verticalScroll(enabled = true, state = rememberScrollState())
                .padding(horizontal = MyOnlineMarketTheme.shapes.paddingBig)
        ) {
            Box(){
                ImageSlider(
                    product = productState.value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(368.dp))
                Image(painter = painterResource(
                    id = if (isFavorite) R.drawable.favorite_heart
                    else R.drawable.favorite_empty ),
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(24.dp)
                        .clickable {
                            if (isFavorite) {
                                viewModel.deleteFavoriteFromDb(productState.value)
                                viewModel.getIdFavoriteProductDb()
                            } else {
                                viewModel.addProductInFavorites(productState.value)
                                viewModel.getIdFavoriteProductDb()
                            }
                        })
            }
            Text(
                text = productState.value.title!!,
                style = MyOnlineMarketTheme.typography.firstTitle,
                color = MyOnlineMarketTheme.colors.secondaryText,
            )
            Text(
                text = productState.value.subtitle!!,
                style = MyOnlineMarketTheme.typography.largeTitle,
                color = MyOnlineMarketTheme.colors.primaryText,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = "Доступно для заказа ${pluralize(productState.value.available!!)}",
                style = MyOnlineMarketTheme.typography.firstText,
                color = MyOnlineMarketTheme.colors.secondaryText,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Row(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .wrapContentSize()
            ){
                StarRatingBar(
                    maxStars = 5,
                    rating = rating!!,
                    modifier = Modifier
                        .selectableGroup()
                        .align(Alignment.CenterVertically),

                )
                Text(
                    text = productState.value.feedback?.rating.toString(),
                    style = MyOnlineMarketTheme.typography.firstText,
                    color = MyOnlineMarketTheme.colors.secondaryText,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(horizontal = 10.dp)
                )
                Text(
                    text = pluralizeReview(productState.value.feedback?.count!!),
                    style = MyOnlineMarketTheme.typography.firstText,
                    color = MyOnlineMarketTheme.colors.secondaryText,
                    modifier = Modifier.align(Alignment.CenterVertically)

                )
            }
            Image(
                painter = painterResource(id = R.drawable.break_line),
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp))
            Row(
                modifier = Modifier.padding(start = 5.dp)
            ){
                Text(
                    text = productState.value.price?.priceWithDiscount + " " + productState.value.price?.unit,
                    style = MyOnlineMarketTheme.typography.priceText,
                    color = MyOnlineMarketTheme.colors.primaryText,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(horizontal = 12.dp)
                        .align(Alignment.CenterVertically)
                ){
                    Text(
                        text = productState.value.price?.price!! + " " + productState.value.price?.unit,
                        style = MyOnlineMarketTheme.typography.firstText,
                        color = MyOnlineMarketTheme.colors.secondaryText,
                        modifier = Modifier
                            .padding(start = MyOnlineMarketTheme.shapes.paddingMini)
                            .align(Alignment.Center)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.line),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(start = MyOnlineMarketTheme.shapes.paddingMini)
                            .align(Alignment.Center))
                }
                Card(
                    shape = RoundedCornerShape(4.dp),
                    colors = CardDefaults.cardColors(MyOnlineMarketTheme.colors.accentColor),
                    modifier = Modifier
                        .padding(start = MyOnlineMarketTheme.shapes.paddingSmall)
                        .height(16.dp)
                        .align(Alignment.CenterVertically)
                ){
                    Text(
                        text = "-"+ productState.value.price?.discount + " %",
                        style = MyOnlineMarketTheme.typography.elementText,
                        color = Color.White,
                        modifier = Modifier
                            .padding(horizontal = MyOnlineMarketTheme.shapes.paddingMini)
                            .align(Alignment.CenterHorizontally))
                }
            }
            Text(
                text = stringResource(R.string.Description),
                style = MyOnlineMarketTheme.typography.firstTitle,
                color = MyOnlineMarketTheme.colors.primaryText,
                modifier = Modifier
                    .padding(top = 24.dp))
            if (showDescription){
                Card(
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MyOnlineMarketTheme.colors.secondaryBackground
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(62.dp)
                        .padding(top = 16.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Text(
                            text = productState.value.title!!,
                            style = MyOnlineMarketTheme.typography.secondTitle,
                            color = MyOnlineMarketTheme.colors.primaryText,
                            modifier = Modifier
                                .padding(top = 12.dp, start = 9.dp)
                                .align(Alignment.CenterStart))
                        Image(
                            painter = painterResource(id = R.drawable.arrow_right),
                            contentDescription = "",
                            modifier = Modifier.align(Alignment.CenterEnd)
                                .padding(top = 12.dp, end = 12.dp))
                    }
                }
                Text(
                    text = productState.value.description!!,
                    style = MyOnlineMarketTheme.typography.firstText,
                    color = MyOnlineMarketTheme.colors.thirdText,
                    modifier = Modifier.padding(top = 8.dp))
            }
            Text(
                text = if (showDescription) "Скрыть"
                    else "Подробнее",
                style = MyOnlineMarketTheme.typography.firstButtonText,
                color = MyOnlineMarketTheme.colors.secondaryText,
                modifier = Modifier
                    .padding(bottom = 32.dp, top = 10.dp)
                    .wrapContentSize()
                    .clickable { showDescription = !showDescription })
            Text(
                text = stringResource(R.string.Сharacteristics),
                style = MyOnlineMarketTheme.typography.firstTitle,
                color = MyOnlineMarketTheme.colors.primaryText,
                modifier = Modifier
                    .padding(bottom = 16.dp))

            Column {
                productState.value.info.forEachIndexed{ index, item ->
                    Box(
                        modifier = Modifier
                            .height(32.dp)
                            .fillMaxWidth()
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.break_line),
                            contentDescription = "",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter))
                        Text(
                            text = item.title!!,
                            style = MyOnlineMarketTheme.typography.firstText,
                            color = MyOnlineMarketTheme.colors.thirdText,
                            modifier = Modifier
                                .padding(vertical = 4.dp)
                                .align(Alignment.BottomStart))
                        Text(
                            text = item.value!!,
                            style = MyOnlineMarketTheme.typography.firstText,
                            color = MyOnlineMarketTheme.colors.thirdText,
                            modifier = Modifier
                                .padding(vertical = 4.dp)
                                .align(Alignment.BottomEnd))

                    }
                }
            }

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .fillMaxWidth()
                    .padding(top = 32.dp)
            ){
                Text(
                    text = stringResource(R.string.compound),
                    style = MyOnlineMarketTheme.typography.firstTitle,
                    color = MyOnlineMarketTheme.colors.primaryText,
                    modifier = Modifier
                        .align(Alignment.CenterStart))
                Image(
                    painter = painterResource(id = R.drawable.copy),
                    contentDescription = "",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .size(16.dp)
                        .align(Alignment.CenterEnd))

            }
            if(showCompound){
                Text(
                    text = productState.value.ingredients!!,
                    style = MyOnlineMarketTheme.typography.firstText,
                    color = MyOnlineMarketTheme.colors.thirdText,
                    modifier = Modifier.padding(top = 16.dp))
            }
            Text(
                text = if (showCompound) "Скрыть"
                else "Подробнее",
                style = MyOnlineMarketTheme.typography.firstButtonText,
                color = MyOnlineMarketTheme.colors.secondaryText,
                modifier = Modifier
                    .padding(bottom = 32.dp, top = 10.dp)
                    .wrapContentSize()
                    .clickable { showCompound = !showCompound })
            Card(
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MyOnlineMarketTheme.colors.accentColor
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp)
                    .padding(top = 16.dp, bottom = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(65.dp)
                        .padding(horizontal = 16.dp)
                ){
                    Row(
                        modifier = Modifier.align(Alignment.CenterStart)
                    ){
                        Text(
                            text = productState.value.price?.priceWithDiscount + " " + productState.value.price?.unit,
                            style = MyOnlineMarketTheme.typography.secondButtonText,
                            color = Color.White,
                            modifier = Modifier
                                .align(Alignment.CenterVertically))
                        Box(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(horizontal = 12.dp)
                                .align(Alignment.CenterVertically)
                        ){
                            Text(
                                text = productState.value.price?.price!! + productState.value.price?.unit,
                                style = MyOnlineMarketTheme.typography.firstCaption,
                                color = MyOnlineMarketTheme.colors.secondaryAccentColor,
                                modifier = Modifier
                                    .padding(start = MyOnlineMarketTheme.shapes.paddingMini)
                                    .align(Alignment.Center)
                            )
                            Image(
                                painter = painterResource(id = R.drawable.pink_line),
                                contentDescription = "",
                                modifier = Modifier
                                    .padding(start = MyOnlineMarketTheme.shapes.paddingMini)
                                    .align(Alignment.Center))
                        }
                    }
                    Text(
                        text = stringResource(R.string.add_product),
                        style = MyOnlineMarketTheme.typography.secondButtonText,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.CenterEnd))

                }

            }
        }
    }

}

fun pluralize(number: Int): String {
    val locale = ULocale.getDefault()
    val pluralRules = PluralRules.forLocale(locale)

    val keyword = pluralRules.select(number.toDouble())
    return when (keyword) {
        "одна" -> "$number штука"
        "две" -> "$number штуки"
        else -> "$number штук"
    }
}
fun pluralizeReview(number: Int): String {
    val locale = ULocale.getDefault()
    val pluralRules = PluralRules.forLocale(locale)

    val keyword = pluralRules.select(number.toDouble())
    return when (keyword) {
        "один"  -> "$number отзыв"
        "два" -> "$number отзыва"
        else -> "$number отзывов"
    }
}
@Preview(showBackground = true)
@Composable
fun PagePreview() {
    val viewModel: MarketViewModel = koinViewModel()
    ProductPage(viewModel)
}