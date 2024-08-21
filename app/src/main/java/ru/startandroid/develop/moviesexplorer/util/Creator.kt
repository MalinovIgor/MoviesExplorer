package ru.startandroid.develop.moviesexplorer.util

import android.app.Activity
import android.content.Context
import ru.startandroid.develop.moviesexplorer.MoviesSearchPresenter
import ru.startandroid.develop.moviesexplorer.PosterPresenter
import ru.startandroid.develop.moviesexplorer.data.MoviesRepositoryImpl
import ru.startandroid.develop.moviesexplorer.data.network.RetrofitNetworkClient
import ru.startandroid.develop.moviesexplorer.domain.api.MoviesInteractor
import ru.startandroid.develop.moviesexplorer.domain.api.MoviesRepository
import ru.startandroid.develop.moviesexplorer.domain.impl.MoviesInteractorImpl
import ru.startandroid.develop.moviesexplorer.presentation.movies.MoviesView
import ru.startandroid.develop.moviesexplorer.presentation.poster.PosterView
import ru.startandroid.develop.moviesexplorer.ui.movies.MoviesAdapter

object Creator {
    private fun getMoviesRepository(context: Context): MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient(context))
    }

    fun provideMoviesInteractor(context: Context): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository(context))
    }

    fun provideMoviesSearchPresenter(
        moviesView: MoviesView,
        context: Context,
        adapter: MoviesAdapter
    ): MoviesSearchPresenter {
        return MoviesSearchPresenter(view = moviesView, context = context)
    }

    fun providePosterPresenter(
        posterView: PosterView,
        imageUrl: String
    ): PosterPresenter {
        return PosterPresenter(posterView, imageUrl)
    }
}