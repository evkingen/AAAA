package com.alohagoha.aaaa.data

import com.alohagoha.aaaa.R
import com.alohagoha.aaaa.entities.Movie

object MoviesDataSource : IDataSource<Movie> {
    val moviesList: List<Movie> = listOf(
            Movie(
                    1,
                    "Avengers: End game",
                    "Action, Adventure, Drama",
                    "13+",
                    4,
                    125,
                    137,
                    false,
                    R.drawable.movie1
            )
    )

    override fun getAllData(): List<Movie> {
        return moviesList
    }
}
