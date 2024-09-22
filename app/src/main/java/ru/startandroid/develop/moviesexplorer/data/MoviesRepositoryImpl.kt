package ru.startandroid.develop.moviesexplorer.data

import android.util.Log
import ru.startandroid.develop.moviesexplorer.data.dto.MovieCastRequest
import ru.startandroid.develop.moviesexplorer.data.dto.MovieCastResponse
import ru.startandroid.develop.moviesexplorer.data.dto.MovieDetailsRequest
import ru.startandroid.develop.moviesexplorer.data.dto.MovieDetailsResponse
import ru.startandroid.develop.moviesexplorer.data.dto.MoviesSearchRequest
import ru.startandroid.develop.moviesexplorer.data.dto.MovieSearchResponse
import ru.startandroid.develop.moviesexplorer.domain.api.MoviesRepository
import ru.startandroid.develop.moviesexplorer.domain.models.LocalStorage
import ru.startandroid.develop.moviesexplorer.domain.models.Movie
import ru.startandroid.develop.moviesexplorer.domain.models.MovieCast
import ru.startandroid.develop.moviesexplorer.domain.models.MovieDetails
import ru.startandroid.develop.moviesexplorer.util.Resource

class MoviesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val localStorage: LocalStorage,
    private val movieCastConverter: MovieCastConverter,
) : MoviesRepository {

    override fun searchMovies(expression: String): Resource<List<Movie>> {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте интернет")
            }

            200 -> {
                val stored = localStorage.getSavedFavorites()
                if ((response as MovieSearchResponse).results.isNotEmpty()) {
                    Resource.Success((response as MovieSearchResponse).results.map {
                        Movie(
                            it.id,
                            it.resultType,
                            it.image,
                            it.title,
                            it.description,
                            inFavorite = stored.contains(it.id),
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
    }    override fun addMovieToFavorites(movie: Movie) {
        localStorage.addToFavorites(movie.id)
    }

    override fun removeMovieFromFavorites(movie: Movie) {
        localStorage.removeFromFavorites(movie.id)
    }

    override fun getMovieCast(movieId: String): Resource<MovieCast> {
        // Поменяли объект dto на нужный Request-объект
        val response = networkClient.doRequest(MovieCastRequest(movieId))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }
            200 -> {
                // Осталось написать конвертацию!
                with(response as MovieCastResponse) {
                    Resource.Success(
                        data = movieCastConverter.convert(response as MovieCastResponse)
                    )
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