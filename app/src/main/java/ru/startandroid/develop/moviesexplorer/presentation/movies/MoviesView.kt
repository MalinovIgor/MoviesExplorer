package ru.startandroid.develop.moviesexplorer.presentation.movies

interface MoviesView {    fun showPlaceholderMessage(isVisible: Boolean)

    fun showMoviesList(isVisible: Boolean)

    fun showProgressBar(isVisible: Boolean)
    fun changePlaceholderText(newPlaceholderText: String)

}