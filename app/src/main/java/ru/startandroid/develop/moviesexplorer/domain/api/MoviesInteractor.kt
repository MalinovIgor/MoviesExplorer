package ru.startandroid.develop.moviesexplorer.domain.api

import ru.startandroid.develop.moviesexplorer.domain.models.Movie
import ru.startandroid.develop.moviesexplorer.domain.models.MovieDetails

interface MoviesInteractor {
    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
    fun searchMovies(expression: String, consumer: MoviesConsumer)
    fun getMovieDetails(id:String, consumer: MovieDetailsConsumer)

    interface MoviesConsumer {
        fun consume(foundMovies: List<Movie>?, errorMessage:String?)
    }
    interface MovieDetailsConsumer  {
        fun consume(movieDetails: MovieDetails?, errorMessage:String?)
    }
}