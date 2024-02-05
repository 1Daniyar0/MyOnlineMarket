package com.example.myonlinemarket.screens.registration_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.models.User
import com.example.myonlinemarket.R
import com.example.myonlinemarket.ui.theme.MyOnlineMarketTheme
import com.example.myonlinemarket.viewModel.MarketViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun RegistrationScreen(viewModel: MarketViewModel = koinViewModel()){
    var phone by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var surname by rememberSaveable { mutableStateOf("") }
    var isNameValid by remember {
        mutableStateOf(false)
    }
    var isSurnameValid by remember{
        mutableStateOf( false)
    }
    var isPhoneValid by remember{
        mutableStateOf( false)
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = MyOnlineMarketTheme.shapes.paddingBig)){
        Column(modifier = Modifier
            .align(Alignment.Center)) {
            RegistrationField(
                text = name,
                label = "Имя",
                format = "[А-Яа-яЁё]*",
                onTextChanged = {
                    name = it
                    isNameValid = isValidText(name)
                },
                onClearText = {
                    name = ""
                    isNameValid = isValidText(name)
                })
            RegistrationField(
                text = surname,
                label = "Фамилия",
                format = "[А-Яа-яЁё]*",
                onTextChanged = {
                    surname = it
                    isSurnameValid = isValidText(it)
                },
                onClearText = {
                    surname = ""
                    isSurnameValid = isValidText(surname)
                })
            PhoneField(
                phone = phone,
                label ="Номер телефона",
                mask = "+7-000-000-00-00",
                maskNumber = '0',
                onPhoneChanged = {
                    phone = it.filter{it.isDigit()}
                    isPhoneValid = phone.length == 10},
                onClearText = {
                    phone = ""
                    isPhoneValid = false})
            SignInButton(onClick = {
                val user = User(name,surname,phone)
                viewModel.addUserInDataBase(user) },
                isNameValid && isSurnameValid && isPhoneValid
            )
            Spacer(modifier = Modifier.height(200.dp))
        }

        Column(modifier = Modifier
            .align(Alignment.BottomCenter)){
            Text(
                text = "Нажимая кнопку “Войти”, Вы принимаете",
                style = MyOnlineMarketTheme.typography.linkText,
                color = MyOnlineMarketTheme.colors.secondaryText,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "условия программы лояльности",
                style = MyOnlineMarketTheme.typography.linkLinedText,
                color = MyOnlineMarketTheme.colors.secondaryText,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
        }

    }
}


@Composable
fun SignInButton(onClick: () -> Unit, clickable: Boolean){
    Button(
        onClick = { onClick() },
        enabled = clickable,
        content = {
                  Text(text = "Войти",
                      style = MyOnlineMarketTheme.typography.secondButtonText
                  )
        },
        shape = MyOnlineMarketTheme.shapes.cornersStyle,
        colors = ButtonDefaults.buttonColors(
            containerColor = MyOnlineMarketTheme.colors.accentColor
        ),
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth())

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationField(
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
        singleLine = true,
        textStyle = MyOnlineMarketTheme.typography.placeHolderText,
        placeholder = { Text(
            text = label,
            color = MyOnlineMarketTheme.colors.secondaryText,
            style = MyOnlineMarketTheme.typography.placeHolderText) },
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

fun isValidText(text: String):Boolean{
    return text.matches(Regex("[А-Яа-яЁё]*")) && text.isNotEmpty()
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
        singleLine = true,
        textStyle = MyOnlineMarketTheme.typography.placeHolderText,
        onValueChange = { it ->
            onPhoneChanged(it.take(mask.count { it == maskNumber }))
        },
        placeholder = {
            Text(text = label,
                style = MyOnlineMarketTheme.typography.placeHolderText,
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
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        visualTransformation = PhoneVisualTransformation(mask, maskNumber),
        shape = MyOnlineMarketTheme.shapes.cornersStyle,
        colors = textFieldColors,
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(),
    )
    Spacer(modifier = Modifier.height(32.dp))
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