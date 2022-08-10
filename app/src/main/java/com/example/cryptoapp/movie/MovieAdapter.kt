package com.example.cryptoapp.movie

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptoapp.databinding.MoviesItemBinding

class MovieAdapter(private val callback: (model: ResultMoviesAndSeriesModel, isFavorite: Boolean) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    var list = listOf<ResultMoviesAndSeriesModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class MovieViewHolder(
        private val callback: (model: ResultMoviesAndSeriesModel, isFavorite: Boolean) -> Unit,
        private val binding: MoviesItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun binding(model: ResultMoviesAndSeriesModel) {
            if (model.isFavorite) {
                binding.favoriteIcon.visibility = View.VISIBLE
                binding.movieCardView.strokeColor = Color.parseColor("#F5A431")
            } else {
                binding.favoriteIcon.visibility = View.GONE
                binding.movieCardView.strokeColor = Color.parseColor("#00000000")
            }
            binding.movieCardView.setOnLongClickListener {

                if (model.isFavorite) {
                    model.isFavorite = false
                    binding.favoriteIcon.visibility = View.GONE
                    binding.movieCardView.strokeColor = Color.parseColor("#00000000")
                    callback.invoke(model, model.isFavorite)
                } else {
                    model.isFavorite = true
                    binding.favoriteIcon.visibility = View.VISIBLE
                    binding.movieCardView.strokeColor = Color.parseColor("#F5A431")
                    callback.invoke(model, model.isFavorite)
                }
                true
            }

            val voteAverage = model.voteAverage
            if (voteAverage > 8) {
                binding.mustWatchTv.visibility = View.VISIBLE
            } else {
                binding.mustWatchTv.visibility = View.GONE
            }
            Glide.with(binding.root.context)
                .load(model.cardViewImagePath)
                .into(binding.movieCardViewImage)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = MoviesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(callback, view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.binding(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}