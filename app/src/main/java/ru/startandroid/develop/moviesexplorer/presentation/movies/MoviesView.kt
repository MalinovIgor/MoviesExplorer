package ru.startandroid.develop.moviesexplorer.presentation.movies

import ru.startandroid.develop.moviesexplorer.domain.models.Movie

interface MoviesView {    fun showPlaceholderMessage(isVisible: Boolean)

    fun showMoviesList(isVisible: Boolean)

    fun showProgressBar(isVisible: Boolean)
    fun changePlaceholderText(newPlaceholderText: String)
    fun updateMoviesList(newMoviesList: List<Movie>)

    fun showToast(message:String)
}