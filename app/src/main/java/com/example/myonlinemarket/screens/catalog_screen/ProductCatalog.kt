package com.example.myonlinemarket.screens.catalog_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.domain.models.Product
import com.example.myonlinemarket.R
import com.example.myonlinemarket.ui.theme.MyOnlineMarketTheme
import com.example.myonlinemarket.viewModel.MarketViewModel


@Composable
fun ProductCatalog(
    products: ArrayList<Product>,
    sortingOption: SortingOption,
    tagOption: String,
    navController: NavHostController,
    viewModel: MarketViewModel){
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
                ProductItem(item, navController = navController, viewModel = viewModel)
            }
        })
}

@Composable
fun ProductItem(
    product: Product,
    viewModel: MarketViewModel,
    navController: NavHostController
){
    viewModel.getIdFavoriteProductDb()
    val isFavoritesState = viewModel.listOfIdFavorites.collectAsState()
    var isFavorite by remember(isFavoritesState.value) {
        mutableStateOf(isFavoritesState.value.contains(product.id))
    }

    Card(
        shape = MyOnlineMarketTheme.shapes.cornersStyle,
        border = BorderStroke(1.dp, MyOnlineMarketTheme.colors.secondaryBackground),
        colors = CardDefaults.cardColors(
            containerColor = MyOnlineMarketTheme.colors.primaryBackground
        ),
        modifier = Modifier
            .padding(4.dp)
            .wrapContentSize()
            .clickable {
                viewModel.selectProduct(product)
                navController.navigate("product_page_screen")
            }
    ) {
        Box(
            modifier = Modifier.height(144.dp)
        ){
            ImageSlider(
                product,
                Modifier
                    .wrapContentSize()
                    .height(130.dp))
            Image(painter = painterResource(
                id = if (isFavorite) R.drawable.favorite_heart
                    else R.drawable.favorite_empty ),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(36.dp)
                    .padding(6.dp)
                    .clickable {
                        if (isFavorite) {
                            viewModel.deleteFavoriteFromDb(product)
                            viewModel.getIdFavoriteProductDb()
                            viewModel.getFavoritesListFromDb()
                        } else {
                            viewModel.addProductInFavorites(product)
                            viewModel.getIdFavoriteProductDb()
                            viewModel.getFavoritesListFromDb()
                        }
                    })
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
