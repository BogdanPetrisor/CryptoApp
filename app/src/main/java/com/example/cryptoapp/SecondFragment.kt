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
import com.example.cryptoapp.persistence.FavoriteMovieDao
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
    private val callback: (model: ResultMoviesAndSeriesModel, view:RecyclerView) -> Unit =
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

        lifecycleScope.launch(Dispatchers.Main) {
            val response =
                apolloClient.query(ShipsQuery()).execute()
            showGallery(TheMovieDBRepository().getTrendingMoviesAndSeries().results)
            showStars(TheMovieDBRepository().getPopularPeople().results)
            showMovies(
                TheMovieDBRepository().getTopRatedMovies().results,
                binding.ratedMoviesRecycleView
            )
            showMovies(
                TheMovieDBRepository().getPopularMovies().results,
                binding.popularMoviesRecycleView
            )
            showMovies(
                TheMovieDBRepository().getAiringTodayMovies().results,
                binding.airingMoviesRecycleView
            )
            showShips(response.data?.launchLatest?.ships as List<ShipsQuery.Ship>)
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
        recyclerView: RecyclerView
    ) {
        val adapter = MovieAdapter{model -> callback(model,recyclerView)  }
        lifecycleScope.launch(Dispatchers.IO) {
            moviesList.map { movie ->
                dao?.queryAfterId(movie.id.toString())?.let {
                    return@map movie.copy(isFavorite = true)
                }
                return@map movie
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