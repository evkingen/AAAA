package com.alohagoha.aaaa.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.alohagoha.aaaa.MovieApp
import com.alohagoha.aaaa.R
import com.alohagoha.aaaa.databinding.FragmentMoviesDetailsBinding
import com.alohagoha.aaaa.entities.Movie
import com.alohagoha.aaaa.ui.rv.adapters.ActorsListAdapter
import com.alohagoha.aaaa.ui.rv.decorators.GridSpaceItemDecoration
import com.bumptech.glide.Glide

class FragmentMoviesDetails : Fragment(R.layout.fragment_movies_details) {

    @Suppress("UNCHECKED_CAST")
    private val movieDetailsModel: IMovieDetailsModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MovieDetailsModel(
                    movieId,
                    (requireContext().applicationContext as MovieApp).moviesRepository
                ) as T
            }
        }
    }
    private val actorsAdapter = ActorsListAdapter(listOf())
    private val moviesDetailsBinding: FragmentMoviesDetailsBinding get() = _moviesDetailsBinding!!
    private var _moviesDetailsBinding: FragmentMoviesDetailsBinding? = null
    private var movieId: Int = NO_ID

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _moviesDetailsBinding = FragmentMoviesDetailsBinding.bind(view)
        movieId =
            (savedInstanceState?.getInt(MOVIE_ID, NO_ID)
                ?: arguments?.getInt(MOVIE_ID, NO_ID))
                ?: NO_ID
        if (movieId == NO_ID) invalidMovieId()
        initRv()
        initBackButton()
        movieDetailsModel.movieDetails.observe(viewLifecycleOwner, ::updateDetails)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(MOVIE_ID, movieId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _moviesDetailsBinding = null
    }

    private fun invalidMovieId() {
        Toast.makeText(
            requireContext(),
            getString(R.string.movies_details_error_message),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun initRv() {
        moviesDetailsBinding.castRv.apply {
            adapter = actorsAdapter
            val spanCount = resources.getInteger(R.integer.actors_span_count)
            val spacing = resources.getDimension(R.dimen.padding_2x)
            layoutManager = GridLayoutManager(context, spanCount)
            addItemDecoration(GridSpaceItemDecoration(spacing, spacing, spanCount))
            setHasFixedSize(true)
        }
    }

    private fun initBackButton() {
        moviesDetailsBinding.back.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }


    private fun updateDetails(movie: Movie) {
        moviesDetailsBinding.apply {
            countReviewTv.text =
                resources.getString(R.string.movie_review, movie.numberOfRatings)
            genreTv.text = movie.genres.joinToString { it.name }
            movieNameTv.text = movie.title
            storylineDescrTv.text = movie.overview
            movieRating.rating = movie.ratings
            movieRestrictionRating.restrictionRating.text =
                resources.getString(R.string.movie_restriction, movie.minimumAge)
            Glide.with(requireActivity())
                .load(movie.backdrop)
                .centerCrop()
                .into(detailMovieIv)
            moviesDetailsBinding.progressBar.visibility = View.GONE
            castTitleTv.isVisible = movie.actors.isNotEmpty()
            actorsAdapter.updateList(movie.actors)
        }
    }


    companion object {
        const val MOVIE_ID = "FragmentMoviesDetails:movieId"
        const val NO_ID = -1

        fun newInstance(movieId: Int) = FragmentMoviesDetails().apply {
            arguments = Bundle().also { it.putInt(MOVIE_ID, movieId) }
        }
    }
}
