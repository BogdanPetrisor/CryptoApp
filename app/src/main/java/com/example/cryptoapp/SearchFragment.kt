package com.example.cryptoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cryptoapp.persistence.FavoriteMovieDatabaseInstance
import com.example.cryptoapp.persistence.FavoriteMovieDatabaseModel
import com.example.cryptoapp.databinding.FragmentSearchBinding
import com.example.cryptoapp.movie.MovieAdapter
import com.example.cryptoapp.movie.ResultMoviesAndSeriesModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        TheMovieDBRepository().apply {
            var searchView: androidx.appcompat.widget.SearchView = binding.searchView
            val dao = context?.let {
                FavoriteMovieDatabaseInstance.getDatabase(it.applicationContext)?.getMovieDao()
            }

            val callback: (model: ResultMoviesAndSeriesModel, isFavorite: Boolean) -> Unit =
                { model: ResultMoviesAndSeriesModel, isFavorite: Boolean ->
                    lifecycleScope.launch(Dispatchers.IO) {
                        if (!isFavorite) {
                            dao!!.insertOne(
                                FavoriteMovieDatabaseModel(
                                    id = model.id.toString(),
                                    name = model.name
                                )
                            )
                        }else{
                            dao!!.deleteOne(model.id.toString())
                        }
                    }
                }
            searchView.setOnQueryTextListener(object :
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    TODO("Not yet implemented")
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        if (newText.isNotEmpty()) {
                            lifecycleScope.launch(Dispatchers.IO) {
                                val searchedMoviesPage1 = getSearchMovies(newText, 1).results
                                val searchedMoviesPage2 = getSearchMovies(newText, 2).results
                                val searchedMovies = searchedMoviesPage1 + searchedMoviesPage2
                                launch(Dispatchers.Main) {
                                    showSearchedMovies(searchedMovies, callback)
                                }
                            }
                        }
                    }
                    return false
                }
            })

        }
    }

    fun showSearchedMovies(
        moviesList: List<ResultMoviesAndSeriesModel>,
        callback: (model: ResultMoviesAndSeriesModel, isFavorite: Boolean) -> Unit
    ) {
        val adapter = MovieAdapter(callback)
        adapter.list = moviesList
        val gridLayoutManager = GridLayoutManager(activity, 3)
        binding.searchRecycleView.layoutManager = gridLayoutManager
        binding.searchRecycleView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
