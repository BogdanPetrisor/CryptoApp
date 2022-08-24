package com.example.cryptoapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.cryptoapp.MovieApplication
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.FragmentHomeScreenBinding
import com.example.cryptoapp.movie.*
import com.example.cryptoapp.ships.ShipsListAdapter
import com.example.cryptoapp.viewModels.HomeScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@Suppress("UNCHECKED_CAST")
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeScreenViewModel by viewModels()
    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        binding.searchButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun initView() {
        showGallery()
        showStars()
        showTopRatedMovies()
        showAiringTodayMovies()
        showPopularMovies()
        showShips()
    }

    private fun showShips() {
        val adapter = ShipsListAdapter()
        viewModel.ships.observe(viewLifecycleOwner) { ships ->
            adapter.list = ships
        }
        binding.shipsRecycleView.adapter = adapter
    }

    private fun showGallery() {
        viewModel.galleryMovies.observe(viewLifecycleOwner) { gallery ->
            binding.viewPager.adapter = ViewPageAdapter().apply {
                submitList(gallery)
            }
        }


        binding.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                binding.indicator.onPageScrollStateChanged(state)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.indicator.onPageSelected(position)
            }
        })
        binding.indicator.setPageSize(
            6,
        )
        binding.indicator.notifyDataChanged()
    }

    private fun showStars() {
        val adapter = StarsAdapter()
        viewModel.movieStars.observe(viewLifecycleOwner) { stars ->
            adapter.submitList(stars)
        }
        binding.starsRecycleView.adapter = adapter
    }

    private fun showTopRatedMovies() {
        val adapter = MovieAdapter(
            { model -> viewModel.longClickCallback(model, binding.ratedMoviesRecycleView) },
            { id -> clickCallback(id) }
        )
        viewModel.topRatedMovies.observe(viewLifecycleOwner) { movies ->
            adapter.list = movies
        }
        binding.ratedMoviesRecycleView.adapter = adapter
    }

    private fun showPopularMovies() {
        val adapter = MovieAdapter(
            { model -> viewModel.longClickCallback(model, binding.popularMoviesRecycleView) },
            { id -> clickCallback(id) }
        )
        viewModel.popularMovies.observe(viewLifecycleOwner) { movies ->
            adapter.list = movies
        }
        binding.popularMoviesRecycleView.adapter = adapter
    }

    private fun showAiringTodayMovies() {
        val adapter = MovieAdapter(
            { model -> viewModel.longClickCallback(model, binding.airingMoviesRecycleView) },
            { id -> clickCallback(id) }
        )
        viewModel.airingTodayMovies.observe(viewLifecycleOwner) { movies ->
            adapter.list = movies
        }
        binding.airingMoviesRecycleView.adapter = adapter
    }

    val clickCallback: (id: Int) -> Unit = { id ->
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(
                id
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}