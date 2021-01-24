package com.alohagoha.aaaa.data

import com.alohagoha.aaaa.entities.Actor
import com.alohagoha.aaaa.entities.Genre
import com.alohagoha.aaaa.entities.Movie

interface IDataSource {
    suspend fun loadMovies(): List<Movie>
    suspend fun loadActors(): List<Actor>
    suspend fun loadGenres(): List<Genre>
}
