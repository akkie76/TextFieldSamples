package jp.co.example.textfieldsamples.ui

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.core.text.isDigitsOnly

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {}
) {
    val visualTransformation = PasswordVisualTransformation('*')

    var text by remember { mutableStateOf("") }

    val transformedText = remember(text, visualTransformation) {
        visualTransformation.filter(AnnotatedString(text))
    }.text.text

    TextField(
        value = text,
        onValueChange = { value ->
            if (value.isDigitsOnly()) {
                text = value
                onValueChange(transformedText)
            }
        },
        modifier = modifier,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}
