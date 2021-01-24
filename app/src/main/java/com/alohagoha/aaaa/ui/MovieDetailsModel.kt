package com.alohagoha.aaaa.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alohagoha.aaaa.entities.Movie
import com.alohagoha.aaaa.repositories.IMoviesRepository
import kotlinx.coroutines.launch

class MovieDetailsModel(private val movieId: Int, private val moviesRepository: IMoviesRepository) :
    ViewModel(),
    IMovieDetailsModel {

    override val movieDetails: MutableLiveData<Movie> = MutableLiveData()

    init {
        startLoadMovieDetails()
    }

    override fun startLoadMovieDetails() {
        viewModelScope.launch {
            moviesRepository.getAllMovies().find { it.id == movieId }?.let {
                movieDetails.value = it
            }
        }
    }
}
