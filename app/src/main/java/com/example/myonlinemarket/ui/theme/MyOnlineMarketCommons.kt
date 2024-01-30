package com.example.myonlinemarket.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp


data class Colors(
    val primaryText: Color,
    val primaryBackground: Color,
    val secondaryText: Color,
    val secondaryBackground: Color,
    val tintColor: Color
)

data class Typography(
    val heading: TextStyle,
    val body: TextStyle,
    val toolbar: TextStyle
)

data class MyShape(
    val paddingSmall: Dp,
    val paddingMedium: Dp,
    val paddingBig: Dp,
    val paddingLarge: Dp,
    val cornersStyle: Shape,
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