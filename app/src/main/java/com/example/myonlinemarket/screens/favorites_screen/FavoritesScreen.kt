package com.example.myonlinemarket.screens.favorites_screen

import android.annotation.SuppressLint
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myonlinemarket.screens.catalog_screen.ProductCatalog
import com.example.myonlinemarket.screens.catalog_screen.SortingOption
import com.example.myonlinemarket.ui.theme.MyOnlineMarketTheme
import com.example.myonlinemarket.viewModel.MarketViewModel

@SuppressLint("MutableCollectionMutableState")
@Composable
fun FavoritesScreen(viewModel: MarketViewModel, navController: NavHostController){
    viewModel.getFavoritesListFromDb()
    val listOfFavorites = viewModel.listOfFavoritesProduct.collectAsState()
    var sortingOption by remember { mutableStateOf(SortingOption.POPULARITY) }
    var tagsOption by remember { mutableStateOf("All") }
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MyOnlineMarketTheme.colors.secondaryBackground
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(61.dp)
                .padding(bottom = 19.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
            ){
                Card(
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MyOnlineMarketTheme.colors.primaryBackground
                    ),
                    modifier = Modifier
                        .padding(3.dp)
                        .weight(1f)
                        .fillMaxSize()
                ){
                    Text(
                        text = "Товары",
                        style = MyOnlineMarketTheme.typography.secondButtonText,
                        color = MyOnlineMarketTheme.colors.primaryText,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 8.dp))
                }
                Card(
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MyOnlineMarketTheme.colors.secondaryBackground
                    ),
                    modifier = Modifier
                        .padding(3.dp)
                        .weight(1f)
                        .fillMaxSize()

                ){
                    Text(
                        text = "Бренды",
                        style = MyOnlineMarketTheme.typography.secondButtonText,
                        color = MyOnlineMarketTheme.colors.secondaryText,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 8.dp))
                }
            }

        }
        ProductCatalog(
            products = listOfFavorites.value,
            sortingOption = sortingOption,
            tagOption = tagsOption,
            navController = navController,
            viewModel = viewModel)
    }
}
