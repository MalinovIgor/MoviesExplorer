package ru.startandroid.develop.moviesexplorer

import android.app.Application
import ru.startandroid.develop.moviesexplorer.ui.movies.MoviesSearchViewModel

class MoviesApplication: Application() {

    var moviesSearchViewModel : MoviesSearchViewModel? = null

}