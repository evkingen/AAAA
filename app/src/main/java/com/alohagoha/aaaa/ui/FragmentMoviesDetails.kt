package com.alohagoha.aaaa.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.alohagoha.aaaa.R
import com.alohagoha.aaaa.data.loadMovies
import com.alohagoha.aaaa.databinding.FragmentMoviesDetailsBinding
import com.alohagoha.aaaa.entities.Movie
import com.alohagoha.aaaa.ui.rv.adapters.ActorsListAdapter
import com.alohagoha.aaaa.ui.rv.decorators.GridSpaceItemDecoration
import com.bumptech.glide.Glide

class FragmentMoviesDetails : Fragment(R.layout.fragment_movies_details) {

    private var _moviesDetailsBinding: FragmentMoviesDetailsBinding? = null
    private val moviesDetailsBinding: FragmentMoviesDetailsBinding
        get() = _moviesDetailsBinding!!
    private var movieId: Int = NO_ID

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _moviesDetailsBinding = FragmentMoviesDetailsBinding.bind(view)
        movieId =
            (savedInstanceState?.getInt(MOVIE_ID, NO_ID) ?: arguments?.getInt(MOVIE_ID, NO_ID))!!
        initRv()
        initBackButton()
        startLoadMovies()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(MOVIE_ID, movieId)
    }

    private fun initBackButton() {
        moviesDetailsBinding.back.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }

    private fun initRv() {
        moviesDetailsBinding.castRv.apply {
            adapter = ActorsListAdapter(listOf())
            val spanCount = resources.getInteger(R.integer.actors_span_count)
            val spacing = resources.getDimension(R.dimen.padding_2x)
            layoutManager = GridLayoutManager(context, spanCount)
            addItemDecoration(GridSpaceItemDecoration(spacing, spacing, spanCount))
            setHasFixedSize(true)
        }
    }

    fun startLoadMovies() {
        lifecycleScope.launchWhenCreated {
            moviesDetailsBinding.progressBar.visibility = View.VISIBLE
            loadMovies(context!!).find { it.id == movieId }?.let {
                initMovieDetails(it)
            }
        }
    }

    private fun initMovieDetails(movie: Movie) {
        moviesDetailsBinding.apply {
            countReviewTv.text =
                context!!.resources.getString(R.string.movie_review, movie.numberOfRatings)
            genreTv.text = movie.genres.joinToString { it.name }
            movieNameTv.text = movie.title
            storylineDescrTv.text = movie.overview
            movieRating.rating = (movie.ratings * 5 / 10)
            movieRestrictionRating.restrictionRating.text =
                context!!.resources.getString(R.string.movie_restriction, movie.minimumAge)
            Glide.with(context!!)
                .load(movie.backdrop)
                .centerCrop()
                .into(detailMovieIv)
            moviesDetailsBinding.progressBar.visibility = View.GONE

            if (movie.actors.isNotEmpty()) {
                castTitleTv.visibility = View.VISIBLE
                (castRv.adapter as ActorsListAdapter).updateList(movie.actors)


            } else {
                castTitleTv.visibility = View.INVISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _moviesDetailsBinding = null
    }

    companion object {
        const val MOVIE_ID = "FragmentMoviesDetails:movieId"
        const val NO_ID = -1

        fun newInstance(movieId: Int) = FragmentMoviesDetails().apply {
            arguments = Bundle().also { it.putInt(MOVIE_ID, movieId) }
        }
    }
}
