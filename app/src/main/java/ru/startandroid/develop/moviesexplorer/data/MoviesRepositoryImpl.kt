package startandroid.develop.moviesexplorer.data

import startandroid.develop.moviesexplorer.data.converters.MovieCastConverter
import startandroid.develop.moviesexplorer.data.dto.MovieCastRequest
import startandroid.develop.moviesexplorer.data.dto.MovieCastResponse
import startandroid.develop.moviesexplorer.data.dto.MovieDetailsRequest
import startandroid.develop.moviesexplorer.data.dto.MovieDetailsResponse
import startandroid.develop.moviesexplorer.data.dto.MoviesSearchRequest
import startandroid.develop.moviesexplorer.data.dto.MoviesSearchResponse
import startandroid.develop.moviesexplorer.domain.api.MoviesRepository
import startandroid.develop.moviesexplorer.domain.models.Movie
import startandroid.develop.moviesexplorer.domain.models.MovieCast
import startandroid.develop.moviesexplorer.domain.models.MovieDetails
import startandroid.develop.moviesexplorer.util.Resource

class MoviesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val movieCastConverter: MovieCastConverter,
) : MoviesRepository {

    override fun searchMovies(expression: String): Resource<List<Movie>> {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }
            200 -> {
                with(response as MoviesSearchResponse) {
                    Resource.Success(results.map {
                        Movie(it.id, it.resultType, it.image, it.title, it.description)
                    })
                }
            }
            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }

    override fun getMovieDetails(movieId: String): Resource<MovieDetails> {
        val response = networkClient.doRequest(MovieDetailsRequest(movieId))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }
            200 -> {
                with(response as MovieDetailsResponse) {
                    Resource.Success(
                        MovieDetails(
                            id, title, imDbRating ?: "", year,
                            countries, genres, directors, writers, stars, plot
                        )
                    )
                }
            }
            else -> {
                Resource.Error("Ошибка сервера")

            }
        }
    }

    override fun getMovieCast(movieId: String): Resource<MovieCast> {
        val response = networkClient.doRequest(MovieCastRequest(movieId))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }
            200 -> {
                Resource.Success(
                    data = movieCastConverter.convert(response as MovieCastResponse)
                )
            }
            else -> {
                Resource.Error("Ошибка сервера")

            }
        }
    }

}
