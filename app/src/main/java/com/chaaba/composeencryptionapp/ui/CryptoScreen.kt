package com.chaaba.composeencryptionapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chaaba.composeencryptionapp.crypto.CryptoData
import java.util.*


@Composable
fun CryptoScreen() {
    var textToEncryptAndDecrypt by remember { mutableStateOf("") }
    val cryptoData = remember {
        CryptoData()
    }

    Scaffold {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            TextField(
                value = textToEncryptAndDecrypt,
                onValueChange = { textToEncryptAndDecrypt = it },
                modifier = Modifier.fillMaxWidth(0.9f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                Button(onClick = {
                    val bytes = textToEncryptAndDecrypt.toByteArray()
                    val str = encode(cryptoData, bytes)
                    textToEncryptAndDecrypt = str
                }) {
                    Text(text = "Encrypt")
                }

                Button(onClick = {

                    val bytes = decode(textToEncryptAndDecrypt)
                    val originalText =
                        cryptoData.decrypt(bytes)
                            ?.decodeToString()
                    textToEncryptAndDecrypt = originalText ?: "Error"

                }) {
                    Text(text = "Decrypt")
                }
            }

        }
    }
}

private fun decode(textToEncryptAndDecrypt: String): ByteArray =
    Base64.getDecoder().decode(textToEncryptAndDecrypt)

private fun encode(
    cryptoData: CryptoData,
    bytes: ByteArray
): String = Base64.getEncoder().encodeToString(cryptoData.encrypt(bytes))

