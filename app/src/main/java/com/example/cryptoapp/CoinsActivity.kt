package com.example.cryptoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cryptoapp.databinding.ActivityMainBinding

class CoinsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
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