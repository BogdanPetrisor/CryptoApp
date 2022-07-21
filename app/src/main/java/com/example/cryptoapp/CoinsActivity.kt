package com.example.cryptoapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class CoinsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val cryptoList = FilesUtils.getCrypto(this)
        val textViewBtc = findViewById<TextView>(R.id.textViewBTC)
        val textViewETH = findViewById<TextView>(R.id.textViewETH)
        val textViewUSDT = findViewById<TextView>(R.id.tvTether)
        for (crypto in cryptoList) {
            when (crypto.name) {
                "Bitcoin" -> textViewBtc.text = crypto.toString()
                "Ethereum" -> textViewETH.text = crypto.toString()
                "Tether" -> textViewUSDT.text = crypto.toString()
            }
        }

    }



}