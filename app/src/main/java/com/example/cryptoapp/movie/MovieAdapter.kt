package com.example.cryptoapp.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.MoviesItemBinding

class MovieAdapter(private val callback: (model: ResultMoviesAndSeriesModel) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    var list = listOf<ResultMoviesAndSeriesModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun modifyOneElement(model: ResultMoviesAndSeriesModel) {
        val position = list.indexOf(model)
        list = list.map {
            if (model.id == it.id) {
                return@map it.copy(isFavorite = !it.isFavorite)
            } else it
        }
        notifyItemChanged(position)
    }

    inner class MovieViewHolder(
        private val callback: (model: ResultMoviesAndSeriesModel) -> Unit,
        private val binding: MoviesItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun binding(model: ResultMoviesAndSeriesModel) {
            setFavorite(model.isFavorite)
            binding.movieCardView.setOnLongClickListener {
                callback(model)
                true
            }


            if (model.voteAverage > 8) {
                binding.mustWatchTv.visibility = View.VISIBLE
            } else {
                binding.mustWatchTv.visibility = View.GONE
            }
            Glide.with(binding.root.context)
                .load(model.cardViewImagePath)
                .into(binding.movieCardViewImage)
        }

        private fun setFavorite(isFavorite: Boolean) {
            if (isFavorite) {
                binding.favoriteIcon.visibility = View.VISIBLE
                binding.movieCardView.strokeColor =
                    ContextCompat.getColor(binding.root.context, R.color.borderColor)
            } else {
                binding.favoriteIcon.visibility = View.GONE
                binding.movieCardView.strokeColor =
                    ContextCompat.getColor(binding.root.context, R.color.transparentColor)
            }
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