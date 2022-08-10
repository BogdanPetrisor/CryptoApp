package com.example.cryptoapp.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptoapp.databinding.GalleryItemBinding
import java.time.LocalDate
import java.time.format.DateTimeParseException

class ViewPageAdapter : ListAdapter<ResultMoviesAndSeriesModel, ViewPageAdapter.ResultViewHolder>(
    object :
        DiffUtil.ItemCallback<ResultMoviesAndSeriesModel>() {
        override fun areItemsTheSame(
            oldItem: ResultMoviesAndSeriesModel,
            newItem: ResultMoviesAndSeriesModel
        ) = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: ResultMoviesAndSeriesModel,
            newItem: ResultMoviesAndSeriesModel
        ) = oldItem == newItem
    }) {

    inner class ResultViewHolder(private val binding: GalleryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(model: ResultMoviesAndSeriesModel) {
            try {
                val releaseDate = LocalDate.parse(model.releaseDate)
                val today = LocalDate.now().minusMonths(2)
                if (releaseDate.isAfter(today)) {
                    binding.cinemaImage.text = "Coming soon"
                } else binding.cinemaImage.text = "Out in cinemas"
                Glide.with(binding.root.context)
                    .load(model.galleryPath)
                    .into(binding.image)
            } catch (ex: DateTimeParseException) {
                binding.cinemaImage.text = "No released date"
                Glide.with(binding.root.context)
                    .load(model.galleryPath)
                    .into(binding.image)

            }
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