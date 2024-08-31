package com.chaaba.composeencryptionapp.utils

import androidx.datastore.core.Serializer
import com.chaaba.composeencryptionapp.crypto.CryptoData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream


//u can add crypto here
object UserSerializer : Serializer<User> {
    private val cryptoData = CryptoData()

    override val defaultValue: User
        get() = User()

    override suspend fun readFrom(input: InputStream): User {
        val bytes = input.readBytes().decodeToString()
        val decodeStr = cryptoData.decrypt(decodeString(bytes))?.decodeToString()

        val res = Gson().fromJson(
            decodeStr, User::class.java
        )
        return res
    }


    override suspend fun writeTo(t: User, output: OutputStream) {
        val json = Gson().toJson(t)
        val bytes = json.toByteArray()
        val str = encodeArray(cryptoData.encrypt(bytes))
        withContext(Dispatchers.IO) {
            output.write(str.toByteArray())
        }
    }
}