package com.example.myonlinemarket.screens.catalog_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
    val isFavorites by remember { mutableStateOf(false) }
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


@Preview(showBackground = true)
@Composable
fun CatalogPreview() {
    MyOnlineMarketTheme {
        ProductFilter(){

        }
    }
}