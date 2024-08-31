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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import com.chaaba.composeencryptionapp.crypto.CryptoData
import com.chaaba.composeencryptionapp.utils.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Composable
fun PrefDataStoreCryptoScreen() {
    var textToEncryptAndDecrypt by remember { mutableStateOf("") }
    val cryptoData = remember { CryptoData() }
    var encryptedText by remember { mutableStateOf<String?>(null) }
    var decryptedText by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()

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
            Text(text = encryptedText ?: "Here the Encrypted Data")

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = decryptedText ?: "Here the Decrypted Data")

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                Button(
                    onClick = {
                        val bytes = textToEncryptAndDecrypt.toByteArray()
                        val str = encodeArray(cryptoData.encrypt(bytes))
                        encryptedText = str
                        coroutine.launch {
                            context.prefDataStore.edit {
                                it[KEY] = str
                            }
                        }

                    }
                ) {
                    Text(text = "Encrypt")
                }

                Button(
                    onClick = {
                        val prefFlow= context.prefDataStore.data
                            .map { preferences ->
                                preferences[KEY] ?: "Error"
                            }
                        coroutine.launch {
                            val bytes = decodeString(prefFlow.first())
                            val decodeStr = cryptoData.decrypt(bytes)?.decodeToString()
                            decryptedText = decodeStr ?: "Error"
                        }
                    }
                ) {
                    Text(text = "Decrypt")
                }
            }

        }
    }
}


