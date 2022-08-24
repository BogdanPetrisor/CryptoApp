package com.example.cryptoapp.crypto

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.CoinItemBinding
import java.lang.StringBuilder


class CoinAdapter(private val onCardClicked: (model: CoinModel) -> Unit) :
    RecyclerView.Adapter<CoinAdapter.CoinViewHolder>() {
    var list = listOf<CoinModel>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class CoinViewHolder(
        private val binding: CoinItemBinding,
        val onCardClicked: (model: CoinModel) -> Unit
    ) : ViewHolder(binding.root) {

        fun binding(model: CoinModel, position: Int) {
            val builder = StringBuilder().apply {
                append("id:")
                append(model.id)
                append("\nrank:")
                append(model.rank)
                append("\nname:")
                append(model.name)
                append("\nsymbol:")
                append(model.symbol)
                append("\ntype:")
                append(model.type)
            }

            binding.tvCoin.text = builder
            when (model.symbol) {
                "BTC" -> binding.imageCoin.setImageResource(R.drawable.bitcoin)
                "DOGE" -> binding.imageCoin.setImageResource(R.drawable.dogecoin)
                "ETH" -> binding.imageCoin.setImageResource(R.drawable.ethereum)
                "TUSD" -> binding.imageCoin.setImageResource(R.drawable.tusd)
                "OKB" -> binding.imageCoin.setImageResource(R.drawable.okb)
                "DOT" -> binding.imageCoin.setImageResource(R.drawable.polkadot)
                "QNT" -> binding.imageCoin.setImageResource(R.drawable.qnt)
                "USDT" -> binding.imageCoin.setImageResource(R.drawable.tether)
                "RUNE" -> binding.imageCoin.setImageResource(R.drawable.thorchain)
                "AVAX" -> binding.imageCoin.setImageResource(R.drawable.avax)

            }
            binding.tvCoin.setOnClickListener {
                onCardClicked(list[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val view = CoinItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinViewHolder(view,onCardClicked)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.binding(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

