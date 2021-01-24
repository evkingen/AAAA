package com.alohagoha.aaaa.data

import com.alohagoha.aaaa.data.dto.JsonActor
import com.alohagoha.aaaa.data.dto.JsonGenre
import com.alohagoha.aaaa.data.dto.JsonMovie
import com.alohagoha.aaaa.entities.Actor
import com.alohagoha.aaaa.entities.Genre
import com.alohagoha.aaaa.entities.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class JsonLoader(private val assetsProvider: IAssetsProvider) : IDataSource {
    private val jsonFormat = Json { ignoreUnknownKeys = true }

    override suspend fun loadMovies(): List<Movie> = withContext(Dispatchers.IO) {
        val genres = loadGenres()
        val actors = loadActors()

        val data = assetsProvider.readAssetFileToString("data.json")
        parseMovies(data, genres, actors)
    }

    override suspend fun loadGenres(): List<Genre> = withContext(Dispatchers.IO) {
        val data = assetsProvider.readAssetFileToString("genres.json")
        parseGenres(data)
    }

    override suspend fun loadActors(): List<Actor> = withContext(Dispatchers.IO) {
        val data = assetsProvider.readAssetFileToString("people.json")
        parseActors(data)
    }

    private fun parseActors(data: String): List<Actor> {
        val jsonActors = jsonFormat.decodeFromString<List<JsonActor>>(data)
        return jsonActors.map { Actor(id = it.id, name = it.name, picture = it.profilePicture) }
    }

    private fun parseGenres(data: String): List<Genre> {
        val jsonGenres = jsonFormat.decodeFromString<List<JsonGenre>>(data)
        return jsonGenres.map { Genre(id = it.id, name = it.name) }
    }

    private fun parseMovies(
        data: String,
        genres: List<Genre>,
        actors: List<Actor>
    ): List<Movie> {
        val genresMap = genres.associateBy { it.id }
        val actorsMap = actors.associateBy { it.id }
        val jsonMovies = jsonFormat.decodeFromString<List<JsonMovie>>(data)

        return jsonMovies.map { jsonMovie ->
            Movie(
                id = jsonMovie.id,
                title = jsonMovie.title,
                overview = jsonMovie.overview,
                poster = jsonMovie.posterPicture,
                backdrop = jsonMovie.backdropPicture,
                ratings = jsonMovie.ratings * 5 / 10,
                numberOfRatings = jsonMovie.votesCount,
                minimumAge = if (jsonMovie.adult) 16 else 13,
                runtime = jsonMovie.runtime,
                genres = jsonMovie.genreIds.mapNotNull {
                    genresMap[it]
                },
                actors = jsonMovie.actors.mapNotNull {
                    actorsMap[it]
                }
            )
        }
    }
}
