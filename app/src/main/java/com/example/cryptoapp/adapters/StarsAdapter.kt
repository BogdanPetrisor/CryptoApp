package com.example.cryptoapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptoapp.databinding.StarsItemBinding
import com.example.cryptoapp.movie.ResultPopularPeopleModel

class StarsAdapter : ListAdapter<ResultPopularPeopleModel, StarsAdapter.ResultViewHolder>(
    object :
        DiffUtil.ItemCallback<ResultPopularPeopleModel>() {
        override fun areItemsTheSame(
            oldItem: ResultPopularPeopleModel,
            newItem: ResultPopularPeopleModel
        ) = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: ResultPopularPeopleModel,
            newItem: ResultPopularPeopleModel
        ) = oldItem == newItem
    }) {

    inner class ResultViewHolder(private val binding: StarsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(model: ResultPopularPeopleModel) {
            binding.starsName.text = model.name
            Glide.with(binding.root.context)
                .load(model.starsViewPath)
                .circleCrop()
                .into(binding.circleImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val view = StarsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.binding(getItem(position))
    }
}