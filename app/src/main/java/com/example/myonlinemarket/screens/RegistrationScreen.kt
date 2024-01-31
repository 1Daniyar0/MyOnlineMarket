package com.example.myonlinemarket.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myonlinemarket.R
import com.example.myonlinemarket.ui.theme.MyOnlineMarketTheme


@Composable
fun RegistrationScreen(){
    var phone by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var surname by rememberSaveable { mutableStateOf("") }

    Column {
        RegistrationInputItem(
            text = name,
            label = "Имя",
            format = "[А-Яа-яЁё]*",
            onTextChanged = {name = it},
            onClearText = {name = ""})
        RegistrationInputItem(
            text = surname,
            label = "Фамилия",
            format = "[А-Яа-яЁё]*",
            onTextChanged = {surname = it},
            onClearText = {surname = ""})
        PhoneField(
            phone = phone,
            label ="Номер телефона",
            mask = "+7-000-000-00-00",
            maskNumber = '0',
            onPhoneChanged = { phone = it },
            onClearText = {phone = ""})
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationInputItem(
    text: String,
    label: String,
    format: String,
    onTextChanged: (String) -> Unit,
    onClearText: () -> Unit){

    val isNameValid = text.matches(Regex(format))
    val textFieldColors = if (isNameValid) {
        TextFieldDefaults.textFieldColors(
            containerColor = MyOnlineMarketTheme.colors.secondaryBackground,
            cursorColor = MyOnlineMarketTheme.colors.primaryText,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedTextColor = MyOnlineMarketTheme.colors.primaryText,
            unfocusedTextColor = MyOnlineMarketTheme.colors.primaryText,

        )
    }
    else{
        TextFieldDefaults.textFieldColors(
            containerColor = MyOnlineMarketTheme.colors.errorColor,
            cursorColor = MyOnlineMarketTheme.colors.primaryText,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedTextColor = MyOnlineMarketTheme.colors.primaryText,
            unfocusedTextColor = MyOnlineMarketTheme.colors.primaryText
        )
    }
    TextField(
        value = text,
        placeholder = { Text(text = label,
            color = MyOnlineMarketTheme.colors.secondaryText) },
        onValueChange = {
            onTextChanged(it)
        },
        trailingIcon = {
            if (text.isNotEmpty()){
                Icon(
                    painter = painterResource(id = R.drawable.clean_string),
                    contentDescription = "",
                    Modifier.clickable {
                        onClearText()
                    })
            }},
        shape = MyOnlineMarketTheme.shapes.cornersStyle,
        colors = textFieldColors,
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneField(
    phone: String,
    label: String,
    modifier: Modifier = Modifier,
    mask: String = "000 000 00 00",
    maskNumber: Char = '0',
    onPhoneChanged: (String) -> Unit,
    onClearText: () -> Unit
) {
    val textFieldColors = TextFieldDefaults.textFieldColors(
            containerColor = MyOnlineMarketTheme.colors.secondaryBackground,
            cursorColor = MyOnlineMarketTheme.colors.primaryText,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedTextColor = MyOnlineMarketTheme.colors.primaryText,
            unfocusedTextColor = MyOnlineMarketTheme.colors.primaryText,)

    TextField(
        value = phone,
        onValueChange = { it ->
            onPhoneChanged(it.take(mask.count { it == maskNumber }))
        },
        placeholder = {
            Text(text = label,
                color = MyOnlineMarketTheme.colors.secondaryText)
        },
        trailingIcon = {
            if (phone.isNotEmpty()){
                Icon(
                    painter = painterResource(id = R.drawable.clean_string),
                    contentDescription = "",
                    Modifier.clickable {
                        onClearText()
                    })
            }},
        colors = textFieldColors,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        visualTransformation = PhoneVisualTransformation(mask, maskNumber),
        modifier = modifier.fillMaxWidth(),
    )
}

class PhoneVisualTransformation(val mask: String, val maskNumber: Char) : VisualTransformation {

    private val maxLength = mask.count { it == maskNumber }

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.length > maxLength) text.take(maxLength) else text

        val annotatedString = buildAnnotatedString {
            if (trimmed.isEmpty()) return@buildAnnotatedString

            var maskIndex = 0
            var textIndex = 0
            while (textIndex < trimmed.length && maskIndex < mask.length) {
                if (mask[maskIndex] != maskNumber) {
                    val nextDigitIndex = mask.indexOf(maskNumber, maskIndex)
                    append(mask.substring(maskIndex, nextDigitIndex))
                    maskIndex = nextDigitIndex
                }
                append(trimmed[textIndex++])
                maskIndex++
            }
            pushStyle(SpanStyle(color = Color.LightGray))
            append(mask.takeLast(mask.length - length))
            toAnnotatedString()
        }

        return TransformedText(annotatedString, PhoneOffsetMapper(mask, maskNumber))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PhoneVisualTransformation) return false
        if (mask != other.mask) return false
        if (maskNumber != other.maskNumber) return false
        return true
    }

    override fun hashCode(): Int {
        return mask.hashCode()
    }
}

private class PhoneOffsetMapper(val mask: String, val numberChar: Char) : OffsetMapping {

    override fun originalToTransformed(offset: Int): Int {
        var noneDigitCount = 0
        var i = 0
        while (i < offset + noneDigitCount) {
            if (mask[i++] != numberChar) noneDigitCount++
        }
        return offset + noneDigitCount
    }

    override fun transformedToOriginal(offset: Int): Int =
        offset - mask.take(offset).count { it != numberChar }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyOnlineMarketTheme {
        RegistrationScreen()
    }
}