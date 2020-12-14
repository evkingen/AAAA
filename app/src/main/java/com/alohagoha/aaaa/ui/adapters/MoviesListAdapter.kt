package com.alohagoha.aaaa.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alohagoha.aaaa.R
import com.alohagoha.aaaa.databinding.MovieItemBinding
import com.alohagoha.aaaa.entities.Movie
import com.alohagoha.aaaa.ui.FragmentMoviesList
import com.bumptech.glide.Glide

class MoviesListAdapter(
        private val clickListener: FragmentMoviesList.OnMovieCardClickListener?,
        private val moviesList: List<Movie>
) : RecyclerView.Adapter<MoviesListAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(private val viewBinding: MovieItemBinding) :
            RecyclerView.ViewHolder(viewBinding.root) {
        init {
            viewBinding.movieCv.apply {
                setOnClickListener {
                    clickListener?.onClickMovie()
                }
                setBackgroundResource(R.drawable.background_cv_borders)
            }
            viewBinding.movieImageIv.clipToOutline = true
        }


        fun bind(movie: Movie) {
            Glide.with(viewBinding.root.context)
                    .load(movie.imageUrl)
                    .into(viewBinding.movieImageIv)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(moviesList[position])
    }

    override fun getItemCount(): Int = moviesList.size

}
