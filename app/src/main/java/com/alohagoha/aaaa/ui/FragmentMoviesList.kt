package com.alohagoha.aaaa.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.alohagoha.aaaa.MovieApp
import com.alohagoha.aaaa.R
import com.alohagoha.aaaa.databinding.FragmentMoviesListBinding
import com.alohagoha.aaaa.ui.rv.adapters.MoviesListAdapter
import com.alohagoha.aaaa.ui.rv.decorators.GridSpaceItemDecoration

class FragmentMoviesList(movieCardListener: OnMovieCardClickListener) :
    Fragment(R.layout.fragment_movies_list) {
    companion object {
        const val FRAGMENT_TAG = "TAG:FragmentMoviesList"
    }

    fun interface OnMovieCardClickListener {
        fun onClickMovie(movieId: Int)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _moviesListBinding = FragmentMoviesListBinding.bind(view)
        initRv()
        startLoadMovies()
    }

    private fun initRv() {
        moviesListBinding.moviesRv.apply {
            adapter = movieAdapter

            layoutManager = GridLayoutManager(
                context,
                resources.getInteger(R.integer.movies_span_count),
                GridLayoutManager.VERTICAL,
                false
            ).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int = when (position) {
                        0 -> spanCount
                        else -> 1
                    }
                }
            }

            val spacing = resources.getDimension(R.dimen.padding_3x)
            val spanCount = resources.getInteger(R.integer.movies_span_count)
            addItemDecoration(GridSpaceItemDecoration(spacing, spacing, spanCount))
            setHasFixedSize(true)

        }
    }

    private fun startLoadMovies() {
        lifecycleScope.launchWhenCreated {
            movieAdapter.updateList(
                (requireContext().applicationContext as MovieApp).jsonLoader.loadMovies()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _moviesListBinding = null
    }

    private val movieAdapter: MoviesListAdapter = MoviesListAdapter(movieCardListener, listOf())
    private var _moviesListBinding: FragmentMoviesListBinding? = null
    private val moviesListBinding get() = _moviesListBinding!!

}
