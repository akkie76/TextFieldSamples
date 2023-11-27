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
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.core.text.isDigitsOnly

@Composable
fun HyphenSeparatorTextField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {}
) {
    val visualTransformation = CreditCardNumberVisualTransformation()

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

class CreditCardNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 16) {
            text.text.substring(0..15)
        } else {
            text.text
        }
        var output = ""
        for (i in trimmed.indices) {
            output += trimmed[i]
            if (i % 4 == 3 && i != 15) output += "-"
        }

        val creditCardOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 3) return offset
                if (offset <= 7) return offset + 1
                if (offset <= 11) return offset + 2
                if (offset <= 16) return offset + 3
                return 19
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 4) return offset
                if (offset <= 9) return offset - 1
                if (offset <= 14) return offset - 2
                if (offset <= 19) return offset - 3
                return 16
            }
        }

        return TransformedText(AnnotatedString(output), creditCardOffsetTranslator)
    }
}
