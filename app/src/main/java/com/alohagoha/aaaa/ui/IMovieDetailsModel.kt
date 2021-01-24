package com.alohagoha.aaaa.ui

import androidx.lifecycle.LiveData
import com.alohagoha.aaaa.entities.Movie

interface IMovieDetailsModel {
    val movieDetails: LiveData<Movie>
    fun startLoadMovieDetails()
}
