package com.example.cryptoapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cryptoapp.databinding.FragmentSearchBinding
import com.example.cryptoapp.adapters.MovieAdapter
import com.example.cryptoapp.movie.ResultMoviesAndSeriesModel
import com.example.cryptoapp.viewModels.SearchMovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val viewModel: SearchMovieViewModel by viewModels()
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
        val searchView: androidx.appcompat.widget.SearchView = binding.searchView
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null) {
                    viewModel.getMoviesByQuery(query)
                    viewModel.movies.observe(viewLifecycleOwner) { movies ->
                        showSearchedMovies(
                            movies
                        )
                    }

                }
                return false
            }

        })


    }

    fun showSearchedMovies(
        moviesList: List<ResultMoviesAndSeriesModel>,
    ) {
        val adapter =
            MovieAdapter({ model -> viewModel.longClickCallback(model, binding.searchRecycleView) },
                { id -> clickCallback(id) }
            )
        adapter.list = moviesList
        val gridLayoutManager = GridLayoutManager(activity, 3)
        binding.searchRecycleView.layoutManager = gridLayoutManager
        binding.searchRecycleView.adapter = adapter
    }

    val clickCallback: (id: Int) -> Unit = { id ->
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToMovieDetailsFragment(
                id
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
