package com.chaaba.composeencryptionapp.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import java.util.Base64


val Context.prefDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

val Context.protoDataStore: DataStore<User> by dataStore(
    fileName = "settings.json",
    serializer = UserSerializer
)

val KEY = stringPreferencesKey("dataToEncrypt")

fun decodeString(str: String): ByteArray = Base64.getDecoder().decode(str)

fun encodeArray(bytes: ByteArray): String = Base64.getEncoder().encodeToString(bytes)

data class User(
    val name: String? = null,
    val token: String? = null,
    val id: Int? = null
)