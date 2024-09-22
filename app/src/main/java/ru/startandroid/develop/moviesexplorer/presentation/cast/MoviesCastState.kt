package startandroid.develop.moviesexplorer.presentation.cast

import startandroid.develop.moviesexplorer.core.ui.RVItem

sealed interface MoviesCastState {

    object Loading : MoviesCastState

    data class Content(
        val fullTitle: String,
        val items: List<RVItem>,
    ) : MoviesCastState

    data class Error(
        val message: String,
    ) : MoviesCastState

}
