package ru.startandroid.develop.moviesexplorer.creator

import android.content.Context
import ru.startandroid.develop.moviesexplorer.PosterPresenter
import ru.startandroid.develop.moviesexplorer.data.MoviesRepositoryImpl
import ru.startandroid.develop.moviesexplorer.data.network.RetrofitNetworkClient
import ru.startandroid.develop.moviesexplorer.domain.api.MoviesInteractor
import ru.startandroid.develop.moviesexplorer.domain.api.MoviesRepository
import ru.startandroid.develop.moviesexplorer.domain.impl.MoviesInteractorImpl
import ru.startandroid.develop.moviesexplorer.presentation.poster.PosterView

object Creator {
    private fun getMoviesRepository(context: Context): MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient(context))
    }

    fun provideMoviesInteractor(context: Context): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository(context))
    }

    fun providePosterPresenter(
        posterView: PosterView,
        imageUrl: String
    ): PosterPresenter {
        return PosterPresenter(posterView, imageUrl)
    }
}