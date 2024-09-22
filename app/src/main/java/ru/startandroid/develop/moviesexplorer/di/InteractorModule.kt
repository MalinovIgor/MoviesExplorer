package startandroid.develop.moviesexplorer.di

import org.koin.dsl.module
import startandroid.develop.moviesexplorer.domain.api.MoviesInteractor
import startandroid.develop.moviesexplorer.domain.impl.MoviesInteractorImpl

val interactorModule = module {

    single<MoviesInteractor> {
        MoviesInteractorImpl(get())
    }

}