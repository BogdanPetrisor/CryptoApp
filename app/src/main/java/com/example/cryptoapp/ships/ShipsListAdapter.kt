package com.example.cryptoapp.ships

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptoapp.ShipsQuery
import com.example.cryptoapp.databinding.ShipItemBinding

class ShipsListAdapter : RecyclerView.Adapter<ShipsListAdapter.ShipViewHolder>() {
    var list = listOf<ShipsQuery.Ship>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ShipViewHolder(
        private val binding: ShipItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun binding(model: ShipsQuery.Ship) {
            binding.shipName.text = model.name
            Glide.with(binding.root.context)
                .load(model.image)
                .circleCrop()
                .into(binding.shipImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShipViewHolder {
        val view = ShipItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShipViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShipViewHolder, position: Int) {
        holder.binding(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }


}