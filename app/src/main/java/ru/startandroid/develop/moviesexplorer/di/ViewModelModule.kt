package startandroid.develop.moviesexplorer.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import startandroid.develop.moviesexplorer.presentation.cast.MoviesCastViewModel
import startandroid.develop.moviesexplorer.presentation.details.AboutViewModel
import startandroid.develop.moviesexplorer.presentation.details.PosterViewModel
import startandroid.develop.moviesexplorer.presentation.movies.MoviesViewModel

val viewModelModule = module {

    viewModel {
        MoviesViewModel(androidContext(), get())
    }

    viewModel {(movieId: String) ->
        AboutViewModel(movieId, get())
    }

    viewModel {(posterUrl: String) ->
        PosterViewModel(posterUrl)
    }

    viewModel { (movieId: String) ->
        MoviesCastViewModel(movieId, get())
    }

}
