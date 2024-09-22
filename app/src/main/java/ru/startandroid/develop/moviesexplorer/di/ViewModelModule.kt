package ru.startandroid.develop.moviesexplorer.di

import android.app.Application
import android.content.Context
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.startandroid.develop.moviesexplorer.MovieDetailsViewModel
import ru.startandroid.develop.moviesexplorer.creator.Creator
import ru.startandroid.develop.moviesexplorer.data.MovieCastConverter
import ru.startandroid.develop.moviesexplorer.data.MoviesRepositoryImpl
import ru.startandroid.develop.moviesexplorer.data.network.RetrofitNetworkClient
import ru.startandroid.develop.moviesexplorer.domain.api.MoviesInteractor
import ru.startandroid.develop.moviesexplorer.domain.api.MoviesRepository
import ru.startandroid.develop.moviesexplorer.domain.impl.MoviesInteractorImpl
import ru.startandroid.develop.moviesexplorer.domain.models.LocalStorage
import ru.startandroid.develop.moviesexplorer.ui.movies.MoviesSearchViewModel
import ru.startandroid.develop.moviesexplorer.ui.poster.PosterViewModel

val ViewModelModule = module  {
    single<MoviesInteractor> {
        MoviesInteractorImpl(get()) // Репозиторий передается через get()
    }

    viewModel {
        MoviesSearchViewModel(application = get(), get())
    }
    viewModel {(movieId: String) ->
       MovieDetailsViewModel(movieId, get())
    }

    factory { MovieCastConverter() }


    single<MoviesRepository> {
        MoviesRepositoryImpl(
            RetrofitNetworkClient(get()), // Передаем контекст через get()
            LocalStorage(get<Context>().getSharedPreferences("local_storage", Context.MODE_PRIVATE)),
            MovieCastConverter()
            // Дополнительные зависимости, если есть
        )
    }

    viewModel {(posterUrl: String) ->
        PosterViewModel(posterUrl)
    }
}