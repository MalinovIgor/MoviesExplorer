package ru.startandroid.develop.moviesexplorer

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.startandroid.develop.moviesexplorer.di.ViewModelModule
import ru.startandroid.develop.moviesexplorer.ui.movies.MoviesSearchViewModel

class MoviesApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MoviesApplication)
            modules(ViewModelModule) // Подключите ваши модули
        }
    }
    var moviesSearchViewModel : MoviesSearchViewModel? = null

}