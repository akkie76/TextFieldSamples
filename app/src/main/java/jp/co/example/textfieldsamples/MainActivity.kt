package jp.co.example.textfieldsamples

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import jp.co.example.textfieldsamples.ui.MainScreen
import jp.co.example.textfieldsamples.ui.theme.TextFieldSamplesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TextFieldSamplesTheme {
                MainScreen()
            }
        }
    }
}
