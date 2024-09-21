package ru.startandroid.develop.moviesexplorer.domain.api

import ru.startandroid.develop.moviesexplorer.domain.models.Movie
import ru.startandroid.develop.moviesexplorer.domain.models.MovieDetails
import ru.startandroid.develop.moviesexplorer.util.Resource

interface MoviesRepository {
    fun searchMovies(expression: String): Resource<List<Movie>>

    fun getMovieDetails(id:String):Resource<MovieDetails>

    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
}