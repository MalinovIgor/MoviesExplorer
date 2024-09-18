package ru.startandroid.develop.moviesexplorer.ui.movies.models

import ru.startandroid.develop.moviesexplorer.domain.models.Movie
import ru.startandroid.develop.moviesexplorer.domain.models.MovieDetails
import ru.startandroid.develop.moviesexplorer.ui.movies.models.MoviesState

sealed interface DetailsState {

        object Loading : DetailsState
        data class Details(
                val details: MovieDetails
        ) : DetailsState

        data class Error(
                val errorMessage: String
        ) : DetailsState

}