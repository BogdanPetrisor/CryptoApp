package com.example.cryptoapp.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.GalleryItemBinding
import com.example.cryptoapp.movie.ResultMoviesAndSeriesModel
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
        @RequiresApi(Build.VERSION_CODES.O)
        fun binding(model: ResultMoviesAndSeriesModel) {
            try {
                if (LocalDate.parse(model.releaseDate).isAfter(LocalDate.now().minusMonths(2))) {
                    binding.cinemaImage.text = binding.root.context.getString(R.string.coming_soon)
                } else binding.cinemaImage.text =
                    binding.root.context.getString(R.string.out_in_cinemas)
            } catch (ex: DateTimeParseException) {
                binding.cinemaImage.text = binding.root.context.getString(R.string.no_released_date)
            }
            Glide.with(binding.root.context)
                .load(model.galleryPath)
                .into(binding.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val view = GalleryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResultViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.binding(getItem(position))
    }


}