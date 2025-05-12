package com.example.nummo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.nummo.ui.theme.NummoTheme
import androidx.activity.compose.setContent
import com.example.nummo.ui.theme.NummoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NummoTheme {
                PreScreen(
                    onLoginClick = {

                    },
                    onSignUpClick = {

                    }
                )
            }
        }
    }
}



