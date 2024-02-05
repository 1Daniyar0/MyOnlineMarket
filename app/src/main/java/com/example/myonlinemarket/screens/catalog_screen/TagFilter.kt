package com.example.myonlinemarket.screens.catalog_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myonlinemarket.R
import com.example.myonlinemarket.ui.theme.MyOnlineMarketTheme

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