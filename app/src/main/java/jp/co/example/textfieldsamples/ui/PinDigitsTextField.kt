package jp.co.example.textfieldsamples.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun PinDigitsTextField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {}
) {
    var text by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }
    val contentColor = LocalContentColor.current

    BasicTextField(
        value = text,
        onValueChange = { newValue ->
            if (newValue.length <= MaxDigits) {
                text = newValue
                onValueChange(text)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        interactionSource = interactionSource,
        modifier = modifier.border(1.dp, contentColor, RoundedCornerShape(8.dp))
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            PinDigits(text, interactionSource.collectIsFocusedAsState().value)
        }
    }
}

@Composable
private fun PinDigits(
    text: String,
    focused: Boolean,
    modifier: Modifier = Modifier
) {
    val focusedColor = LocalTextSelectionColors.current.backgroundColor
    val contentColor = LocalContentColor.current
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.padding(16.dp)
    ) {
        repeat(MaxDigits) { i ->
            Text(
                text = text.getOrNull(i)?.toString() ?: " ",
                fontFamily = FontFamily.Monospace,
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.drawWithContent {
                    if (i == text.length && focused) {
                        drawRect(color = focusedColor)
                    }
                    drawContent()
                    if (i >= text.length) {
                        drawLine(
                            color = contentColor,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
                }
            )
        }
    }
}

private const val MaxDigits = 5
