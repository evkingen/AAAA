package com.alohagoha.aaaa.ui

import androidx.lifecycle.LiveData
import com.alohagoha.aaaa.entities.Movie

interface IListMoviesModel {
    val moviesList: LiveData<List<Movie>>
    fun startLoadMoviesList()
}
