package com.example.cryptoapp

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cryptoapp.databinding.ActivityCoinDetailsBinding


class CoinDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCoinDetailsBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val coinId = intent.getStringExtra(EXTRA_ID)
        val coin = coinId?.let { FilesUtils.getCoinByID(this, coinId) }
        binding.tvTitle.text = "1. ${coin?.name} \n(${coin?.symbol})"
        when(coin?.isActive){
            true->binding.tvIsActive.text = "active"
            false->{binding.tvIsActive.text = "inactive"
                    binding.tvIsActive.setTextColor(Color.RED)}
            else -> {}
        }
    }
}