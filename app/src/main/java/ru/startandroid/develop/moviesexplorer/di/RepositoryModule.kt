package startandroid.develop.moviesexplorer.di

import org.koin.dsl.module
import startandroid.develop.moviesexplorer.data.MoviesRepositoryImpl
import startandroid.develop.moviesexplorer.data.converters.MovieCastConverter
import startandroid.develop.moviesexplorer.domain.api.MoviesRepository

val repositoryModule = module {

    factory { MovieCastConverter() }

    single<MoviesRepository> {
        MoviesRepositoryImpl(get(), get())
    }

}
