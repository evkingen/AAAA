package com.alohagoha.aaaa.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alohagoha.aaaa.entities.Movie
import com.alohagoha.aaaa.repositories.IMoviesRepository
import kotlinx.coroutines.launch

class ListMoviesModel(private val moviesRepository: IMoviesRepository) : ViewModel(),
    IListMoviesModel {

    override val moviesList: MutableLiveData<List<Movie>> = MutableLiveData()

    init {
        startLoadMoviesList()
    }

    override fun startLoadMoviesList() {
        viewModelScope.launch {
            moviesList.value = moviesRepository.getAllMovies()
        }
    }
}
