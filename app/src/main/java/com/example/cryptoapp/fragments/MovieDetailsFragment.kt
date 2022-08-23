package com.example.cryptoapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.cryptoapp.MovieApplication
import com.example.cryptoapp.databinding.FragmentMovieDetailsBinding
import com.example.cryptoapp.movie.MovieAdapter
import com.example.cryptoapp.viewModels.HomeScreenViewModel
import com.example.cryptoapp.viewModels.MovieDetailsViewModel
import com.example.cryptoapp.viewModels.MovieDetailsViewModelFactory

class MovieDetailsFragment : Fragment() {
    private val viewModel: MovieDetailsViewModel by viewModels{
        MovieDetailsViewModelFactory(
            requireContext().applicationContext as MovieApplication
        )
    }
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private var movieId: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        val safeArgs: MovieDetailsFragmentArgs by navArgs()
        movieId = safeArgs.movieId
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setMovie(movieId.toString())
        setupViews()
    }

    fun setupViews(){
        viewModel.title.observe(viewLifecycleOwner){
            binding.title.text = it
        }

        viewModel.overview.observe(viewLifecycleOwner){
            binding.overview.text = it
        }

        viewModel.popularity.observe(viewLifecycleOwner){
            binding.popularity.text = it.toString()
        }
        viewModel.posterImage.observe(viewLifecycleOwner){
            Glide.with(binding.root.context)
                .load(it)
                .into(binding.posterImage)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
