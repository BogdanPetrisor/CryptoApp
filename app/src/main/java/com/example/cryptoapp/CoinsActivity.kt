package com.example.cryptoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptoapp.databinding.ActivityMainBinding

const val TAG = "CoinsActivity"
const val EXTRA_ID = "ID"

class CoinsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val cryptoList = FilesUtils.getCrypto(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = CoinAdapter{
            val intent = Intent(this, CoinDetailsActivity::class.java)
            intent.putExtra(EXTRA_ID, it.id)
            startActivity(intent)
        }
        adapter.list = cryptoList.sortedBy { it.rank }
        binding.recyclerView.adapter = adapter


    }
//        for (crypto in cryptoList) {
//            when (crypto.name) {
//                "Bitcoin" -> binding.textViewBTC.text = crypto.toString()
//                "Ethereum" -> binding.textViewETH.text = crypto.toString()
//                "Tether" -> binding.textViewUSDT.text = crypto.toString()
//            }
//        }
//
//        setupViews()
//    }
//
//    private fun setupViews() {
//        binding.textViewBTC.setOnClickListener {
//            navigateWithExtra("bitcoin")
//        }
//        binding.textViewETH.setOnClickListener {
//            navigateWithExtra("ethereum")
//        }
//        binding.textViewUSDT.setOnClickListener {
//            navigateWithExtra("tether")
//        }
//    }
//
//
//    private fun navigateWithExtra(extra: String) {
//        val intent = Intent(this, CoinDetailsActivity::class.java)
//        intent.putExtra(EXTRA_ID, extra)
//        startActivity(intent)
//    }


    }
