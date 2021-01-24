package com.alohagoha.aaaa.data

import com.alohagoha.aaaa.entities.Movie
import com.alohagoha.aaaa.repositories.IMoviesRepository

class MoviesRepository(private val dataSource: IDataSource) : IMoviesRepository {
    override suspend fun getAllMovies(): List<Movie> = dataSource.loadMovies()
    override suspend fun getMovieById(movieId: Int): Movie? =
        dataSource.loadMovies().find { it.id == movieId }
}
