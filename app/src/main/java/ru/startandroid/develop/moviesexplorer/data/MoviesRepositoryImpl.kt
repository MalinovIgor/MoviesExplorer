package ru.startandroid.develop.moviesexplorer.data

import android.util.Log
import ru.startandroid.develop.moviesexplorer.data.dto.MoviesSearchRequest
import ru.startandroid.develop.moviesexplorer.data.dto.MoviesSearchResponse
import ru.startandroid.develop.moviesexplorer.domain.api.MoviesRepository
import ru.startandroid.develop.moviesexplorer.domain.models.Movie

class MoviesRepositoryImpl(private val networkClient: NetworkClient) : MoviesRepository {

    override fun searchMovies(expression: String): List<Movie> {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        if (response.resultCode == 200) {
            return (response as MoviesSearchResponse).results.map {
                Movie(it.id, it.resultType, it.image, it.title, it.description) }
        } else {
            return emptyList()
        }
    }
}