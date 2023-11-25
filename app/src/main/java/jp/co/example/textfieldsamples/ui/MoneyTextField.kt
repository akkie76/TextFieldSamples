package jp.co.example.textfieldsamples.ui

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.core.text.isDigitsOnly

@Composable
fun MoneyTextField(
    onChanged: (String) -> Unit = {}
) {
    val visualTransformation = MoneyVisualTransformation()

    var text by remember { mutableStateOf("") }

    val transformedText = remember(text, visualTransformation) {
        visualTransformation.filter(AnnotatedString(text))
    }.text.text

    TextField(
        value = text,
        onValueChange = { value ->
            if (value.isDigitsOnly()) {
                text = value
                onChanged(transformedText)
            }
        },
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

private class MoneyVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val output = buildString {
            val reversed = text.text.reversed()
            for (index in reversed.indices) {
                if (index > 0 && index % 3 == 0) {
                    append(',')
                }
                append(reversed[index])
            }
        }.reversed()

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                val totalSeparatorCount = (text.length - 1) / 3
                val rightSeparatorCount = (text.length - 1 - offset) / 3
                val leftSeparatorCount = totalSeparatorCount - rightSeparatorCount
                return offset + leftSeparatorCount
            }

            override fun transformedToOriginal(offset: Int): Int {
                val totalSeparatorCount = (text.length - 1) / 3
                val rightSeparatorCount = (output.length - offset) / 4
                val leftSeparatorCount = totalSeparatorCount - rightSeparatorCount
                return offset - leftSeparatorCount
            }
        }

        return TransformedText(
            text = AnnotatedString(output),
            offsetMapping = offsetMapping
        )
    }
}
