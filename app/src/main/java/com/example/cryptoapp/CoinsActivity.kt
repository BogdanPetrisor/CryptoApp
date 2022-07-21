package com.example.cryptoapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.cryptoapp.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class CoinsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val cryptoList = FilesUtils.getCrypto(this)

        for (crypto in cryptoList) {
            when (crypto.name) {
                "Bitcoin" -> binding.textViewBTC.text = crypto.toString()
                "Ethereum" -> binding.textViewETH.text = crypto.toString()
                "Tether" -> binding.textViewUSDT.text = crypto.toString()
            }
        }

    }


}