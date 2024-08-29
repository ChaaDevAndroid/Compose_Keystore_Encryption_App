package com.chaaba.composeencryptionapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.chaaba.composeencryptionapp.ui.theme.ComposeEncryptionAppTheme
import com.chaaba.composeencryptionapp.ui.CryptoScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeEncryptionAppTheme {
                CryptoScreen()
            }
        }
    }
}

