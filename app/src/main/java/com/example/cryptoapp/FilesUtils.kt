package com.example.cryptoapp

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class FilesUtils {
    companion object {
        fun getCrypto(context: Context): List<CoinModel> {
            lateinit var jsonString: String
            try {
                jsonString = context.assets.open("crypto.json")
                    .bufferedReader()
                    .use { it.readText() }
            } catch (ioException: IOException) {

            }

            val listCryptoType = object : TypeToken<List<CoinModel>>() {}.type
            return Gson().fromJson(jsonString, listCryptoType)
        }


        fun getCoinByID(context: Context, id: String): CoinDetailsModel? {
            val jsonString: String
            try {
                jsonString = context.assets.open("$id.json")
                    .bufferedReader()
                    .use { it.readText() }
                return Gson().fromJson(jsonString, CoinDetailsModel::class.java)
            } catch (ioException: Exception) {
                Log.e(TAG, ioException.message + "")
            }
            return null
        }
    }
}