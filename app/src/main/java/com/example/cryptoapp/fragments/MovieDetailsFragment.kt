package com.example.cryptoapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.cryptoapp.databinding.FragmentMovieDetailsBinding
import com.example.cryptoapp.viewModels.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private val viewModel: MovieDetailsViewModel by viewModels()
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
        binding.lifecycleOwner = viewLifecycleOwner
        binding.movieDetailsViewModel = viewModel
        setupMovieImage()
    }

    fun setupMovieImage(){
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
