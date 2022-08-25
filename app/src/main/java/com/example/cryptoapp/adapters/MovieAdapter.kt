package com.example.cryptoapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.MoviesItemBinding
import com.example.cryptoapp.movie.ResultMoviesAndSeriesModel

class MovieAdapter(
    private val longClickCallback: (model: ResultMoviesAndSeriesModel) -> Unit,
    private val clickCallback: (id: Int) -> Unit
) :
    ListAdapter<ResultMoviesAndSeriesModel, MovieAdapter.MovieViewHolder>(object :
        DiffUtil.ItemCallback<ResultMoviesAndSeriesModel>() {
        override fun areItemsTheSame(
            oldItem: ResultMoviesAndSeriesModel,
            newItem: ResultMoviesAndSeriesModel
        ): Boolean =
            oldItem.id == newItem.id


        override fun areContentsTheSame(
            oldItem: ResultMoviesAndSeriesModel,
            newItem: ResultMoviesAndSeriesModel
        ): Boolean =
            oldItem == newItem
    }
    ) {


    inner class MovieViewHolder(
        private val longClickCallback: (model: ResultMoviesAndSeriesModel) -> Unit,
        private val clickCallback: (id: Int) -> Unit,
        private val binding: MoviesItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun binding(model: ResultMoviesAndSeriesModel) {
            setFavorite(model.isFavorite)
            binding.movieCardView.setOnLongClickListener {
                longClickCallback(model)
                true
            }
            binding.movieCardView.setOnClickListener {
                clickCallback(model.id)
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
        return MovieViewHolder(longClickCallback, clickCallback, view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.binding(getItem(position))
    }


}