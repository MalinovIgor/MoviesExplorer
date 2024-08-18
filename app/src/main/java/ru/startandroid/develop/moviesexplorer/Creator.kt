package ru.startandroid.develop.moviesexplorer

import ru.startandroid.develop.moviesexplorer.data.MoviesRepositoryImpl
import ru.startandroid.develop.moviesexplorer.data.network.RetrofitNetworkClient
import ru.startandroid.develop.moviesexplorer.domain.api.MoviesInteractor
import ru.startandroid.develop.moviesexplorer.domain.api.MoviesRepository
import ru.startandroid.develop.moviesexplorer.domain.impl.MoviesInteractorImpl

object Creator {
    private fun getMoviesRepository(): MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideMoviesInteractor(): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository())
    }
}