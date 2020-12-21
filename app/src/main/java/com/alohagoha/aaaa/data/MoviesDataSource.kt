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
                    4f,
                    125,
                    137,
                    false,
                    R.drawable.movie1
            ),
            Movie(
                    2,
                    "Tenet",
                    "Action, Sci-Fi, Thriller",
                    "16+",
                    5f,
                    98,
                    97,
                    true,
                    R.drawable.movie2
            ),
            Movie(
                    3,
                    "Black Widow",
                    "Action, Adventure, Sci-Fi",
                    "13+",
                    4f,
                    38,
                    102,
                    false,
                    R.drawable.movie3
            ),
            Movie(
                    4,
                    "Wonder Woman 1984",
                    "Action, Adventure, Fantasy",
                    "13+",
                    5f,
                    74,
                    120,
                    false,
                    R.drawable.movie4
            ),
            Movie(
                    1,
                    "Avengers: End game",
                    "Action, Adventure, Drama",
                    "13+",
                    4f,
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
