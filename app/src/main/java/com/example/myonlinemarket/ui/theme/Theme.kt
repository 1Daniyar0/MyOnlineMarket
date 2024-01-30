package com.example.myonlinemarket.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun MyOnlineMarketTheme(
    textSize: Size = Size.Medium,
    paddingSize: Size = Size.Medium,
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
        heading = TextStyle(
            fontSize = when (textSize) {
                Size.Small -> 24.sp
                Size.Medium -> 28.sp
                Size.Big -> 32.sp
            },
            fontWeight = FontWeight.Bold
        ),
        body = TextStyle(
            fontSize = when (textSize) {
                Size.Small -> 14.sp
                Size.Medium -> 16.sp
                Size.Big -> 18.sp
            },
            fontWeight = FontWeight.Normal
        ),
        toolbar = TextStyle(
            fontSize = when (textSize) {
                Size.Small -> 14.sp
                Size.Medium -> 16.sp
                Size.Big -> 18.sp
            },
            fontWeight = FontWeight.Medium
        )
    )

    val shapes = MyShape(
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
