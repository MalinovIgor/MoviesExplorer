package ru.startandroid.develop.moviesexplorer.data

import android.util.Log
import ru.startandroid.develop.moviesexplorer.data.dto.MoviesSearchRequest
import ru.startandroid.develop.moviesexplorer.data.dto.MoviesSearchResponse
import ru.startandroid.develop.moviesexplorer.domain.api.MoviesRepository
import ru.startandroid.develop.moviesexplorer.domain.models.Movie
import ru.startandroid.develop.moviesexplorer.util.Resource

class MoviesRepositoryImpl(private val networkClient: NetworkClient) : MoviesRepository {

    override fun searchMovies(expression: String): Resource<List<Movie>> {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        return when (response.resultCode ) {
            -1 -> {
                Resource.Error("Проверьте интернет")
            }

            200 -> {
                if ((response as MoviesSearchResponse).results.isNotEmpty())
                {                Resource.Success((response as MoviesSearchResponse).results.map {
                    Movie(
                        it.id,
                        it.resultType,
                        it.image,
                        it.title,
                        it.description
                    )
                })
            }
            else {Resource.Error("Ничего не найдено!")}}

            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }
}