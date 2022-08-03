package com.example.cryptoapp.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptoapp.databinding.GalleryItemBinding

class ViewPageAdapter() : ListAdapter<ResultModel,ViewPageAdapter.ResultViewHolder>(
    object :
        DiffUtil.ItemCallback<ResultModel>() {
        override fun areItemsTheSame(
            oldItem: ResultModel,
            newItem: ResultModel
        ) = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: ResultModel,
            newItem: ResultModel
        ) = oldItem == newItem
    }) {

    inner class ResultViewHolder(private val binding: GalleryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun binding(model: ResultModel) {
            Glide.with(binding.root.context)
                .load(model.fullPath)
                .into(binding.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val view = GalleryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.binding(getItem(position))
    }


}