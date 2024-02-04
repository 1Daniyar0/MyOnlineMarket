package com.example.myonlinemarket.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun MyOnlineMarketTheme(
    textSize: Size = Size.Medium,
    corners: Corners = Corners.Rounded,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        baseDarkPalette
    } else {
        baseLightPalette
    }

    val typography = Typography(
        largeTitle = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = sfProDisplayFamily
        ),
         firstTitle = TextStyle(
             fontSize = 16.sp,
             fontWeight = FontWeight.Medium,
             fontFamily = sfProDisplayFamily
         ),
        secondTitle = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = sfProDisplayFamily
        ),
        thirdTitle = TextStyle(
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = sfProDisplayFamily
        ),
        forthTitle = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = sfProDisplayFamily
        ),
        firstText = TextStyle(
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = sfProDisplayFamily
        ),
        firstCaption = TextStyle(
            fontSize = 10.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = sfProDisplayFamily
        ),
        firstButtonText = TextStyle(
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = sfProDisplayFamily
        ),
        secondButtonText = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = sfProDisplayFamily
        ),
        elementText = TextStyle(
            fontSize = 9.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = sfProDisplayFamily
        ),
        priceText = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = sfProDisplayFamily
        ),
        placeHolderText = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = sfProDisplayFamily
        ),
        linkText = TextStyle(
            fontSize = 10.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = sfProDisplayFamily,
        ),
        linkLinedText = TextStyle(
            fontSize = 10.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = sfProDisplayFamily,
            textDecoration = TextDecoration.Underline
        )
    )

    val shapes = MyShape(
        paddingMini = 6.dp,
        paddingSmall = 8.dp,
        paddingMedium = 12.dp,
        paddingBig =  16.dp,
        paddingLarge =  20.dp,
        cornersStyle = when(corners){
            Corners.Flat -> RoundedCornerShape(0.dp)
            Corners.Rounded -> RoundedCornerShape(8.dp)
        }
    )


    CompositionLocalProvider(
        LocalColors provides colors,
        LocalShape provides shapes,
        LocalTypography provides typography,
        content = content
    )
}
