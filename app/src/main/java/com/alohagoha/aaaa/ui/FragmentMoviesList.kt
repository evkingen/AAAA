package com.alohagoha.aaaa.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.alohagoha.aaaa.R
import com.alohagoha.aaaa.data.MoviesDataSource
import com.alohagoha.aaaa.databinding.FragmentMoviesListBinding
import com.alohagoha.aaaa.ui.rv.adapters.MoviesListAdapter
import com.alohagoha.aaaa.ui.rv.decorators.GridSpaceItemDecoration

class FragmentMoviesList : Fragment() {

    private var clickListener: OnMovieCardClickListener? = null
    private var _moviesListBinding: FragmentMoviesListBinding? = null
    private val moviesListBinding get() = _moviesListBinding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as OnMovieCardClickListener).let {
            clickListener = it
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _moviesListBinding = FragmentMoviesListBinding.inflate(inflater, container, false)
        initRv()
        return moviesListBinding.root
    }

    private fun initRv() {
        moviesListBinding.moviesRv.apply {
            adapter = MoviesListAdapter(context, clickListener, MoviesDataSource.moviesList)

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

    override fun onDetach() {
        super.onDetach()
        clickListener = null
    }

    interface OnMovieCardClickListener {
        fun onClickMovie()
    }
}
