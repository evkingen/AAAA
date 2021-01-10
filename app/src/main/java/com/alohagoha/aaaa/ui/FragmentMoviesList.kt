package com.alohagoha.aaaa.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.alohagoha.aaaa.R
import com.alohagoha.aaaa.data.loadMovies
import com.alohagoha.aaaa.databinding.FragmentMoviesListBinding
import com.alohagoha.aaaa.ui.rv.adapters.MoviesListAdapter
import com.alohagoha.aaaa.ui.rv.decorators.GridSpaceItemDecoration

class FragmentMoviesList : Fragment(R.layout.fragment_movies_list) {

    var clickListener: OnMovieCardClickListener? = null
    private var _moviesListBinding: FragmentMoviesListBinding? = null
    private val moviesListBinding get() = _moviesListBinding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _moviesListBinding = FragmentMoviesListBinding.bind(view)
        initRv()
        startLoadMovies()
    }

    fun startLoadMovies() {
        lifecycleScope.launchWhenCreated {
            (moviesListBinding.moviesRv.adapter as MoviesListAdapter).updateList(loadMovies(context!!))
        }
    }

    private fun initRv() {
        moviesListBinding.moviesRv.apply {
            adapter = MoviesListAdapter(clickListener, listOf())

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

    override fun onDestroyView() {
        super.onDestroyView()
        _moviesListBinding = null
    }

    fun interface OnMovieCardClickListener {
        fun onClickMovie(movieId: Int)
    }

    companion object {
        const val FRAGMENT_TAG = "TAG:FragmentMoviesList"
    }
}
