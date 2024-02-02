package com.example.myonlinemarket.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import com.example.myonlinemarket.R


data class Colors(
    val primaryText: Color,
    val secondaryText: Color,
    val thirdText: Color,
    val forthText: Color,
    val primaryBackground: Color,
    val secondaryBackground: Color,
    val accentColor: Color,
    val secondaryAccentColor: Color,
    val errorColor: Color
)

data class Typography(
    val largeTitle: TextStyle,
    val firstTitle: TextStyle,
    val secondTitle: TextStyle,
    val thirdTitle: TextStyle,
    val forthTitle: TextStyle,
    val firstText: TextStyle,
    val firstCaption: TextStyle,
    val firstButtonText: TextStyle,
    val secondButtonText: TextStyle,
    val elementText: TextStyle,
    val priceText: TextStyle,
    val placeHolderText: TextStyle,
    val linkText: TextStyle,
    val linkLinedText: TextStyle
)

data class MyShape(
    val paddingSmall: Dp,
    val paddingMedium: Dp,
    val paddingBig: Dp,
    val paddingLarge: Dp,
    val cornersStyle: Shape,
)

val sfProDisplayFamily = FontFamily(
    Font(R.font.sf_pro_display_regular, FontWeight.Normal),
    Font(R.font.sf_pro_display_medium, FontWeight.Medium)
)

object MyOnlineMarketTheme {
    val colors: Colors
        @Composable
        get() = LocalColors.current
    val typography: Typography
        @Composable
        get() = LocalTypography.current
    val shapes: MyShape
        @Composable
        get() = LocalShape.current
}

enum class Size{
    Small, Medium, Big
}

enum class Corners {
    Flat, Rounded
}


val LocalColors = staticCompositionLocalOf<Colors> {
    error("No colors provided")
}

val LocalTypography = staticCompositionLocalOf<Typography> {
    error("No font provided")
}

val LocalShape = staticCompositionLocalOf<MyShape> {
    error("No shapes provided")
}