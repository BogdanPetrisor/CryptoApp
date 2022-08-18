package com.example.cryptoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.cryptoapp.persistence.FavoriteMovieDatabaseInstance
import com.example.cryptoapp.persistence.FavoriteMovieDatabaseModel
import com.example.cryptoapp.databinding.FragmentHomeScreenBinding
import com.example.cryptoapp.movie.*
import com.example.cryptoapp.persistence.FavoriteMovieDao
import com.example.cryptoapp.ships.ShipsListAdapter
import com.example.cryptoapp.viewModels.HomeScreenViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@Suppress("UNCHECKED_CAST")
class SecondFragment : Fragment() {
    private val viewModel: HomeScreenViewModel by viewModels()
    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!
    private val dao: FavoriteMovieDao? by lazy {
        FavoriteMovieDatabaseInstance.getDatabase(requireContext())?.getMovieDao()
    }

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
            val searchFragment = SearchFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(
                    R.id.fragment_container_view_tag,
                    searchFragment,
                    "SearchFragment"
                )
                ?.addToBackStack(null)
                ?.commit()
        }
    }

    private val callback: (model: ResultMoviesAndSeriesModel, view: RecyclerView) -> Unit =
        { model, view ->
            lifecycleScope.launch(Dispatchers.IO) {
                if (model.isFavorite) {

                    dao?.deleteOne(model.id.toString())
                } else {
                    dao?.insertOne(
                        FavoriteMovieDatabaseModel(
                            model.id.toString(),
                            model.name
                        )
                    )
                }
            }
            (view.adapter as? MovieAdapter)?.modifyOneElement(model)
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
        val adapter = MovieAdapter { model -> callback(model, binding.ratedMoviesRecycleView) }
        viewModel.topRatedMovies.observe(viewLifecycleOwner) { movies ->
            adapter.list = movies
        }
        binding.ratedMoviesRecycleView.adapter = adapter
    }

    private fun showPopularMovies() {
        val adapter = MovieAdapter { model -> callback(model, binding.popularMoviesRecycleView) }
        viewModel.popularMovies.observe(viewLifecycleOwner) { movies ->
            adapter.list = movies
        }
        binding.popularMoviesRecycleView.adapter = adapter
    }

    private fun showAiringTodayMovies() {
        val adapter = MovieAdapter { model -> callback(model, binding.airingMoviesRecycleView) }
        viewModel.airingTodayMovies.observe(viewLifecycleOwner) { movies ->
            adapter.list = movies
        }
        binding.airingMoviesRecycleView.adapter = adapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}