package ru.startandroid.develop.moviesexplorer.ui.movies.models

import ru.startandroid.develop.moviesexplorer.domain.models.MovieDetails

sealed interface PosterState {

    object Loading : PosterState
    data class Poster(
        val url: String
    ) : PosterState

    data class Error(
        val errorMessage: String
    ) : MoviesState

}