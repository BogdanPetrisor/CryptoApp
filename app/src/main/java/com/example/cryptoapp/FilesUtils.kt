package com.example.cryptoapp

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class FilesUtils {
    companion object {
         fun getCrypto(context: Context): List<Crypto> {
            //helper class->pun in alta clasa functia
            //mainactivity -> CoinsActivity
            //dimens
            //solve all warnings
            //sp la dimensiuni text
            //dp la restul
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
}