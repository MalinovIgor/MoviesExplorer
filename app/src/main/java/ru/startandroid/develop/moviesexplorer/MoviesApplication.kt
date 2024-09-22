package startandroid.develop.moviesexplorer

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import startandroid.develop.moviesexplorer.di.dataModule
import startandroid.develop.moviesexplorer.di.interactorModule
import startandroid.develop.moviesexplorer.di.repositoryModule
import startandroid.develop.moviesexplorer.di.viewModelModule

class MoviesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin {
            androidContext(this@MoviesApplication)
            modules(dataModule, repositoryModule, interactorModule, viewModelModule)
        }
    }
}