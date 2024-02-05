package com.example.myonlinemarket.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.domain.models.Product
import com.example.myonlinemarket.screens.catalog_screen.ImageSlider
import com.example.myonlinemarket.ui.theme.MyOnlineMarketTheme

@Composable
fun ProductPage(product: Product){
    Surface(modifier = Modifier.fillMaxSize()) {
        ImageSlider(product = product)
    }
}

@Preview(showBackground = true)
@Composable
fun PagePreview() {

}