package ru.startandroid.develop.moviesexplorer.domain.api

import ru.startandroid.develop.moviesexplorer.domain.models.Movie

interface MoviesRepository {
    fun searchMovies(expression: String): List<Movie>
}