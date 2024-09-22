package startandroid.develop.moviesexplorer.domain.api

import startandroid.develop.moviesexplorer.domain.models.Movie
import startandroid.develop.moviesexplorer.domain.models.MovieCast
import startandroid.develop.moviesexplorer.domain.models.MovieDetails
import startandroid.develop.moviesexplorer.util.Resource

interface MoviesRepository {
    fun searchMovies(expression: String): Resource<List<Movie>>
    fun getMovieDetails(movieId: String): Resource<MovieDetails>
    fun getMovieCast(movieId: String): Resource<MovieCast>
}
