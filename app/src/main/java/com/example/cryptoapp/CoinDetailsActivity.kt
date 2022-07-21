package com.example.cryptoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cryptoapp.databinding.ActivityCoinDetailsBinding


class CoinDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCoinDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityCoinDetailsBinding.inflate(layoutInflater)
            setContentView(binding.root)
            val coinId = intent.getStringExtra(EXTRA_ID)
            val coin = coinId?.let { FilesUtils.getCoinByID(this, coinId) }
            binding.textView.text = coin.toString()
    }
}