package startandroid.develop.moviesexplorer.presentation.cast

import startandroid.develop.moviesexplorer.core.ui.RVItem
import startandroid.develop.moviesexplorer.domain.models.MovieCastPerson


sealed interface MoviesCastRVItem : RVItem {

    data class HeaderItem(
        val headerText: String,
    ) : MoviesCastRVItem

    data class PersonItem(
        val data: MovieCastPerson,
    ) : MoviesCastRVItem

}
