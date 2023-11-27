package jp.co.example.textfieldsamples.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen() {
    Scaffold { paddingValues ->
        Surface(
            modifier = Modifier.padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = "PasswordTextField",
                    modifier = Modifier.padding(top = 12.dp)
                )
                PasswordTextField()

                Text(
                    text = "HyphenSeparatorTextField",
                    modifier = Modifier.padding(top = 12.dp)
                )
                HyphenSeparatorTextField()

                Text(
                    text = "CommaSeparatorTextField",
                    modifier = Modifier.padding(top = 12.dp)
                )
                CommaSeparatorTextField()

                Text(
                    text = "PinDigitsTextField",
                    modifier = Modifier.padding(top = 12.dp)
                )
                PinDigitsTextField()
            }
        }
    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    MainScreen()
}
