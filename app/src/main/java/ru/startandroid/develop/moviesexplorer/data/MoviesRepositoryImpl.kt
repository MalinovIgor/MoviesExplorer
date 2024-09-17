package ru.startandroid.develop.moviesexplorer.data

import ru.startandroid.develop.moviesexplorer.data.dto.MovieDetailsRequest
import ru.startandroid.develop.moviesexplorer.data.dto.MovieDetailsResponse
import ru.startandroid.develop.moviesexplorer.data.dto.MoviesSearchRequest
import ru.startandroid.develop.moviesexplorer.data.dto.MovieSearchResponse
import ru.startandroid.develop.moviesexplorer.domain.api.MoviesRepository
import ru.startandroid.develop.moviesexplorer.domain.models.Movie
import ru.startandroid.develop.moviesexplorer.domain.models.MovieDetails
import ru.startandroid.develop.moviesexplorer.util.Resource

class MoviesRepositoryImpl(private val networkClient: NetworkClient) : MoviesRepository {

    override fun searchMovies(expression: String): Resource<List<Movie>> {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте интернет")
            }

            200 -> {
                if ((response as MovieSearchResponse).results.isNotEmpty()) {
                    Resource.Success((response as MovieSearchResponse).results.map {
                        Movie(
                            it.id,
                            it.resultType,
                            it.image,
                            it.title,
                            it.description
                        )
                    })
                } else {
                    Resource.Error("Ничего не найдено!")
                }
            }

            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }

    override fun getMovieDetails(id: String): Resource<MovieDetails> {
        val response = networkClient.doRequest(MovieDetailsRequest(id))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте интернет")
            }

            200 -> {
                val movieDetailsResponse = response as MovieDetailsResponse
                if (movieDetailsResponse.title.isNotEmpty()) {
                    val movieDetails = MovieDetails(
                        movieDetailsResponse.id,
                        movieDetailsResponse.title,
                        movieDetailsResponse.imDbRating,
                        movieDetailsResponse.year,
                        movieDetailsResponse.countries,
                        movieDetailsResponse.genres,
                        movieDetailsResponse.directors,
                        movieDetailsResponse.writers,
                        movieDetailsResponse.stars,
                        movieDetailsResponse.plot
                    )
                    Resource.Success(movieDetails)


                } else {
                    Resource.Error("Ничего не найдено!")
                }
            }

            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }
}