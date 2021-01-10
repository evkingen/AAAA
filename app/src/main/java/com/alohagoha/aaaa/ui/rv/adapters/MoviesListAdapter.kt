package com.alohagoha.aaaa.ui.rv.adapters

import android.graphics.Outline
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.recyclerview.widget.RecyclerView
import com.alohagoha.aaaa.R
import com.alohagoha.aaaa.databinding.ViewHolderHeaderBinding
import com.alohagoha.aaaa.databinding.ViewHolderMovieBinding
import com.alohagoha.aaaa.entities.Movie
import com.alohagoha.aaaa.ui.FragmentMoviesList
import com.bumptech.glide.Glide

class MoviesListAdapter(
    private val clickListener: FragmentMoviesList.OnMovieCardClickListener?,
    private var moviesList: List<Movie>
) : RecyclerView.Adapter<MoviesListAdapter.MovieViewHolder>() {


    fun updateList(newMoviesList: List<Movie>) {
        moviesList = newMoviesList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        when (viewType) {
            HEADER_TYPE -> HeaderViewHolder(
                ViewHolderHeaderBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            MOVIE_TYPE -> DataViewHolder(
                ViewHolderMovieBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("Некорректный тип для представления!")
        }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        when (holder) {
            is DataViewHolder -> holder.bind(getMovieItem(position))
        }
    }

    private fun getMovieItem(position: Int) = moviesList[position - 1]

    override fun getItemViewType(position: Int): Int = when (position) {
        0 -> HEADER_TYPE
        else -> MOVIE_TYPE
    }

    override fun getItemCount(): Int = moviesList.size + 1

    abstract class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class HeaderViewHolder(viewBinding: ViewHolderHeaderBinding) : MovieViewHolder(viewBinding.root)
    inner class DataViewHolder(private val viewBinding: ViewHolderMovieBinding) :
        MovieViewHolder(viewBinding.root) {
        init {
            viewBinding.movieCv.apply {
                setOnClickListener { clickListener?.onClickMovie(getMovieItem(adapterPosition).id) }
                setBackgroundResource(R.drawable.background_cv_borders)
            }

            viewBinding.movieImageIv.apply {
                outlineProvider = object : ViewOutlineProvider() {
                    override fun getOutline(view: View?, outline: Outline?) {
                        val curveRadius =
                            context.resources.getDimension(R.dimen.corner_background_movie_screen)
                        outline?.setRoundRect(
                            0,
                            0,
                            view!!.width,
                            (view.height + curveRadius).toInt(),
                            curveRadius
                        )
                    }
                }
                clipToOutline = true
            }
        }

        fun bind(movie: Movie) {
            viewBinding.apply {
                genreTv.text = movie.genres.joinToString { it.name }
                movieNameTv.text = movie.title
                movieRating.rating = (movie.ratings * 5 / 10)
                with(root.context.resources) {
                    durationTv.text = getString(R.string.movie_duration, movie.runtime)
                    countReviewTv.text = getString(R.string.movie_review, movie.numberOfRatings)
                    movieRestrictionRating.restrictionRating.text =
                        getString(R.string.movie_restriction, movie.minimumAge)
                }
                Glide.with(root.context)
                    .load(movie.poster)
                    .into(viewBinding.movieImageIv)
            }
        }
    }

    companion object {
        const val HEADER_TYPE = R.layout.view_holder_header
        const val MOVIE_TYPE = R.layout.view_holder_movie
    }
}
