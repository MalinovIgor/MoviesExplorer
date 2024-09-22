package startandroid.develop.moviesexplorer.presentation.details

import startandroid.develop.moviesexplorer.domain.models.MovieDetails

sealed interface AboutState {

    data class Content(
            val movie: MovieDetails
    ) : AboutState

    data class Error(
            val message: String
    ) : AboutState

}