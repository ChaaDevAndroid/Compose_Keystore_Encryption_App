package com.chaaba.composeencryptionapp.ui

import android.widget.Toast
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
import com.chaaba.composeencryptionapp.utils.protoDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@Composable
fun ProtoDataStoreCryptoScreen() {
    var userToStore by remember { mutableStateOf("") }
    var userStored by remember { mutableStateOf("") }
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
                value = userToStore,
                onValueChange = { userToStore = it },
                modifier = Modifier.fillMaxWidth(0.9f)
            )


            Spacer(modifier = Modifier.height(16.dp))
            Text(text = userStored)

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                Button(
                    onClick = {
                        coroutine.launch {
                            context.protoDataStore.updateData {
                                it.copy(name = userToStore, token = "A", id = 1)
                            }
                        }
                    }
                ) {
                    Text(text = "Save")
                }

                Button(
                    onClick = {
                        context.protoDataStore.data.onEach {
                            userStored = it.name ?: "No Data"
                        }.catch {
                            Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show()
                        }.launchIn(coroutine)

                    }
                ) {
                    Text(text = "Get")
                }
            }

        }
    }
}