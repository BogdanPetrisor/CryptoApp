package com.example.cryptoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.cryptoapp.persistence.FavoriteMovieDatabaseInstance
import com.example.cryptoapp.persistence.FavoriteMovieDatabaseModel
import com.example.cryptoapp.databinding.FragmentHomeScreenBinding
import com.example.cryptoapp.movie.*
import com.example.cryptoapp.ships.ShipsListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@Suppress("UNCHECKED_CAST")
class SecondFragment : Fragment() {
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
        val dao = context?.let {
            FavoriteMovieDatabaseInstance.getDatabase(it.applicationContext)?.getMovieDao()
        }

        val movieDBRepository = TheMovieDBRepository()
        lifecycleScope.launch(Dispatchers.IO) {


            val response =
                apolloClient.query(ShipsQuery()).execute()
            val shipsList = response.data?.launchLatest?.ships
            val galleryList = movieDBRepository.getTrendingMoviesAndSeries().results
            val starList = movieDBRepository.getPopularPeople().results
            val topRatedMoviesList = movieDBRepository.getTopRatedMovies().results
            val popularMoviesList = movieDBRepository.getPopularMovies().results
            val airingTodayMoviesList = movieDBRepository.getAiringTodayMovies().results

            val callback: (model: ResultMoviesAndSeriesModel, isFavorite: Boolean) -> Unit =
                { model: ResultMoviesAndSeriesModel, isFavorite: Boolean ->
                    lifecycleScope.launch(Dispatchers.IO) {

                        if (isFavorite) {
                            dao!!.insertOne(
                                FavoriteMovieDatabaseModel(
                                    id = model.id.toString(),
                                    name = model.name
                                )
                            )
                        } else {
                            dao!!.deleteOne(model.id.toString())
                        }
                    }
                }
            launch(Dispatchers.Main) {
                showGallery(galleryList)
                showStars(starList)
                showMovies(topRatedMoviesList, callback, binding.ratedMoviesRecycleView)
                showMovies(popularMoviesList, callback, binding.popularMoviesRecycleView)
                showMovies(airingTodayMoviesList, callback, binding.airingMoviesRecycleView)
                showShips(shipsList as List<ShipsQuery.Ship>)
            }
        }

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


    private fun showShips(shipsList: List<ShipsQuery.Ship>) {
        val adapter = ShipsListAdapter()
        adapter.list = shipsList
        binding.shipsRecycleView.adapter = adapter
    }

    private fun showGallery(galleryList: List<ResultMoviesAndSeriesModel>) {
        binding.viewPager.adapter = ViewPageAdapter().apply {
            submitList(galleryList)
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

    private fun showStars(starList: List<ResultPopularPeopleModel>) {
        val adapter = StarsAdapter()
        adapter.submitList(starList)
        binding.starsRecycleView.adapter = adapter
    }

    private fun showMovies(
        moviesList: List<ResultMoviesAndSeriesModel>,
        callback: (model: ResultMoviesAndSeriesModel, isFavorite: Boolean) -> Unit,
        recyclerView: RecyclerView
    ) {
        val adapter = MovieAdapter(callback)
        lifecycleScope.launch(Dispatchers.IO) {
            val dao = context?.let {
                FavoriteMovieDatabaseInstance.getDatabase(it.applicationContext)?.getMovieDao()
            }
            for (movie in moviesList) {
                val favoriteMovie = dao?.queryAfterId(movie.id.toString())
                if (favoriteMovie != null)
                    movie.isFavorite = true

            }
            lifecycleScope.launch(Dispatchers.Main) {
                adapter.list = moviesList
                recyclerView.adapter = adapter
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}