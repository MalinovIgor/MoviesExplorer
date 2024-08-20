package ru.startandroid.develop.moviesexplorer.domain.api

import ru.startandroid.develop.moviesexplorer.domain.models.Movie

interface MoviesInteractor {
    fun searchMovies(expression: String, consumer: MoviesConsumer)

    interface MoviesConsumer {
        fun consume(foundMovies: List<Movie>?, errorMessage:String?)
    }
}