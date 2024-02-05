package com.example.myonlinemarket.screens.profile_screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.domain.models.User
import com.example.myonlinemarket.R
import com.example.myonlinemarket.navigation.Destinations
import com.example.myonlinemarket.ui.theme.MyOnlineMarketTheme
import com.example.myonlinemarket.viewModel.MarketViewModel

@Composable
fun ProfileScreen(navController: NavHostController,viewModel: MarketViewModel){
    viewModel.getUserFroDb()
    val user = viewModel.userFromDb.collectAsState()
    val listOfTitle = arrayListOf(
        stringResource(R.string.favorites),
        stringResource(R.string.Mall),
        stringResource(R.string.feedback),
        stringResource(R.string.document),
        stringResource(R.string.returns)
    )
    val listOfDescription = arrayListOf(
        stringResource(R.string.favorites),
        "",
        "",
        "",
        ""
    )
    val listOfTitleIcon = arrayListOf(
        painterResource(id = R.drawable.favorite_empty),
        painterResource(id = R.drawable.mall),
        painterResource(id = R.drawable.feedback),
        painterResource(id = R.drawable.document),
        painterResource(id = R.drawable.back_arrow)
    )
    val funcButton = painterResource(id = R.drawable.arrow_right)

    Box(
        modifier = Modifier.padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {
        Column{
            ProfileCard(
                titleText = user.value.name + " " + user.value.surname,
                secondText = phoneFormat(user.value.phone),
                titleIcon = painterResource(id = R.drawable.profile),
                funcIcon = painterResource(id = R.drawable.exit),
                onClick = {})
            Spacer(modifier = Modifier.size(24.dp))
            ListOfCard(
                titleText = listOfTitle,
                secondText = listOfDescription,
                titleIcon = listOfTitleIcon,
                funcIcon = funcButton,
                navController = navController)
        }
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MyOnlineMarketTheme.colors.secondaryBackground
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(83.dp)
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .clickable {
                    viewModel.deleteUserFroDb()
                    navController.navigate("registration_screen")
                }
        ) {
            Text(
                text = stringResource(R.string.exit),
                style = MyOnlineMarketTheme.typography.secondButtonText,
                color = MyOnlineMarketTheme.colors.primaryText,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 18.dp))
        }
    }
}

@Composable
fun ProfileCard(
    titleText: String,
    secondText: String,
    titleIcon: Painter,
    funcIcon:Painter,
    onClick:() -> Unit
){
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MyOnlineMarketTheme.colors.secondaryBackground
        ),
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ){
            Row(
                modifier = Modifier
                    .height(62.dp)
                    .align(Alignment.CenterStart)
            ) {
                Image(
                    painter = titleIcon,
                    contentDescription = "",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(top = 12.dp, end = 16.dp, bottom = 12.dp)
                        .align(Alignment.CenterVertically))
                Box(
                    modifier = Modifier
                        .height(35.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = titleText,
                        style = MyOnlineMarketTheme.typography.secondTitle,
                        color = MyOnlineMarketTheme.colors.primaryText,
                        modifier = Modifier
                            .align(
                                if (secondText.isNotEmpty()) Alignment.TopStart
                                else Alignment.CenterStart
                            ))
                    if (secondText.isNotEmpty()){
                        Text(
                            text = secondText,
                            style = MyOnlineMarketTheme.typography.firstCaption,
                            color = MyOnlineMarketTheme.colors.secondaryText,
                            modifier = Modifier
                                .align(Alignment.BottomStart))
                    }
                }
            }
            Image(
                painter = funcIcon,
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .padding(top = 12.dp, end = 3.dp, bottom = 12.dp)
                    .align(Alignment.CenterEnd))

        }
    }
}

@Composable
fun ListOfCard(
    titleText: ArrayList<String>,
    secondText: ArrayList<String>,
    titleIcon: ArrayList<Painter>,
    funcIcon: Painter,
    navController: NavHostController
){
    LazyColumn(
        content = {
        itemsIndexed(titleText){index, item ->
            ProfileCard(
                titleText = titleText[index],
                secondText = secondText[index],
                titleIcon = titleIcon[index],
                funcIcon = funcIcon,
                onClick = {if (index == 0) navController.navigate(Destinations.FavoritesScreen.route)}
                )
            Spacer(modifier = Modifier.size(8.dp))
        }
    })
}

fun phoneFormat(phone: String): String {
    var newPhone = StringBuilder()
    phone.forEachIndexed{ index, c ->
        when(index){
            0 -> newPhone.append("+7")
            2 -> newPhone.append(' ')
            5 -> newPhone.append(' ')
            8 -> newPhone.append('-')
            10 -> newPhone.append('-')
        }
        newPhone.append(c)
    }
    Log.e("newPhone",newPhone.toString())
    return newPhone.toString()
}