package com.alohagoha.aaaa.ui.rv.adapters

import android.content.Context
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

class MoviesListAdapter(private val context: Context,
                        private val clickListener: FragmentMoviesList.OnMovieCardClickListener?,
                        private val moviesList: List<Movie>
) : RecyclerView.Adapter<MoviesListAdapter.MovieViewHolder>() {
    companion object {
        const val HEADER_TYPE = R.layout.view_holder_header
        const val MOVIE_TYPE = R.layout.view_holder_movie
    }

    abstract class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class HeaderViewHolder(viewBinding: ViewHolderHeaderBinding) : MovieViewHolder(viewBinding.root)
    inner class DataViewHolder(private val viewBinding: ViewHolderMovieBinding) : MovieViewHolder(viewBinding.root) {
        init {
            viewBinding.movieCv.apply {
                setOnClickListener {
                    clickListener?.onClickMovie()
                }
                setBackgroundResource(R.drawable.background_cv_borders)
            }

            viewBinding.movieImageIv.apply {
                val curveRadius = context.resources.getDimension(R.dimen.corner_background_movie_screen)
                outlineProvider = object : ViewOutlineProvider() {
                    override fun getOutline(view: View?, outline: Outline?) {
                        outline?.setRoundRect(
                                0, 0, view!!.width, (view.height + curveRadius).toInt(), curveRadius)
                    }
                }
                clipToOutline = true
            }
        }

        fun bind(movie: Movie) {
            viewBinding.apply {
                genreTv.text = movie.genre
                movieNameTv.text = movie.name
                movieRating.rating = movie.rating
                durationTv.text = context.resources.getString(R.string.movie_duration, movie.duration)
                countReviewTv.text = context.resources.getString(R.string.movie_review, movie.countReview)
                like.isSelected = movie.isFavorite
                movieRestrictionRating.restrictionRating.text = movie.restriction_rating
                Glide.with(root.context)
                        .load(movie.imageUrl)
                        .into(viewBinding.movieImageIv)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder = when (viewType) {
        HEADER_TYPE -> HeaderViewHolder(ViewHolderHeaderBinding.inflate(LayoutInflater.from(context), parent, false))
        MOVIE_TYPE -> DataViewHolder(ViewHolderMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        else -> throw IllegalArgumentException("Некорректный тип для представления!")
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        when (holder) {
            is DataViewHolder -> holder.bind(getItemView(position))
        }
    }

    private fun getItemView(position: Int) = moviesList[position - 1]

    override fun getItemViewType(position: Int): Int = when (position) {
        0 -> HEADER_TYPE
        else -> MOVIE_TYPE
    }

    override fun getItemCount(): Int = moviesList.size + 1

}
