package com.example.myonlinemarket.screens.catalog_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myonlinemarket.R
import com.example.myonlinemarket.ui.theme.MyOnlineMarketTheme

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