package com.example.cryptoapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val cryptoList = getCrypto(this)

    }
    fun getCrypto(context: Context): List<Crypto> {

        lateinit var jsonString: String
        try {
            jsonString = context.assets.open("crypto.json")
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {

        }

        val listCryptoType = object : TypeToken<List<Crypto>>() {}.type
        return Gson().fromJson(jsonString, listCryptoType)
    }

}