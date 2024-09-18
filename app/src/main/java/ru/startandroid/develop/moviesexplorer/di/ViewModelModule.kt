package ru.startandroid.develop.moviesexplorer.di

import android.app.Application
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.startandroid.develop.moviesexplorer.MovieDetailsViewModel
import ru.startandroid.develop.moviesexplorer.creator.Creator
import ru.startandroid.develop.moviesexplorer.ui.movies.MoviesSearchViewModel
import ru.startandroid.develop.moviesexplorer.ui.poster.PosterViewModel

val ViewModelModule = module  {
    single { Creator.provideMoviesInteractor(get()) }
    viewModel {
        MoviesSearchViewModel(application = Application())
    }
    viewModel {(movieId: String) ->
       MovieDetailsViewModel(movieId, get())
    }

    viewModel {(posterUrl: String) ->
        PosterViewModel(posterUrl)
    }
}