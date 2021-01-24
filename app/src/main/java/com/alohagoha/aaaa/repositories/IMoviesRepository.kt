package com.alohagoha.aaaa.repositories

import com.alohagoha.aaaa.entities.Movie

interface IMoviesRepository {
    suspend fun getAllMovies(): List<Movie>
    suspend fun getMovieById(movieId: Int): Movie?
}
