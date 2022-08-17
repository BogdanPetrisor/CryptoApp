package com.example.cryptoapp

import android.content.Context
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO: fun with functions
        TheMovieDBRepository().apply {
            val searchView: androidx.appcompat.widget.SearchView = binding.searchView
            val dao = context?.let {
                FavoriteMovieDatabaseInstance.getDatabase(it.applicationContext)?.getMovieDao()
            }

            val callback: (model: ResultMoviesAndSeriesModel) -> Unit =
                { model: ResultMoviesAndSeriesModel->
                    lifecycleScope.launch(Dispatchers.IO) {
                        if (!model.isFavorite) {
                            dao?.insertOne(
                                FavoriteMovieDatabaseModel(
                                    id = model.id.toString(),
                                    name = model.name
                                )
                            )
                        } else {
                            dao?.deleteOne(model.id.toString())
                        }
                    }
                }
            val sharedPref = activity?.getSharedPreferences("searchMovie", Context.MODE_PRIVATE)
            val editor = sharedPref?.edit()

            searchView.setOnQueryTextListener(object :
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {

                    editor?.apply {
                        //TODO: you do not need builde variable and text and list
                        val builder: StringBuilder = StringBuilder(sharedPref.getString("TEXT", ""))
                        val text = builder.append("$query/")
                        putString("text", text.toString())
                        val list: MutableList<String> = text.split("/") as MutableList<String>
                        list.reverse()
                        println(list)
                        commit()
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        if (newText.isNotEmpty()) {
                            lifecycleScope.launch(Dispatchers.IO) {
                                delay(1000)
                                //TODO: ROBERT SEARCH
                                val searchedMovies = getSearchMovies(
                                    newText,
                                    1
                                ).results.plus(getSearchMovies(newText, 2).results)
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
        callback: (model: ResultMoviesAndSeriesModel) -> Unit
    ) {
        val adapter = MovieAdapter(callback)
        lifecycleScope.launch(Dispatchers.IO) {
            val dao = context?.let {
                FavoriteMovieDatabaseInstance.getDatabase(it.applicationContext)?.getMovieDao()
            }
            //TODO: see robert's case
            for (movie in moviesList) {
                val favoriteMovie = dao?.queryAfterId(movie.id.toString())
               // if (favoriteMovie != null)

            }
            lifecycleScope.launch(Dispatchers.Main) {
                adapter.list = moviesList
                val gridLayoutManager = GridLayoutManager(activity, 3)
                binding.searchRecycleView.layoutManager = gridLayoutManager
                binding.searchRecycleView.adapter = adapter
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
