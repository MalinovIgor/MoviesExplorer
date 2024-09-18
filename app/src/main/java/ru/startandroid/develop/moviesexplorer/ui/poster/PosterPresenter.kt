package ru.startandroid.develop.moviesexplorer.ui.poster

import android.app.Activity
import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import ru.startandroid.develop.moviesexplorer.presentation.movies.MoviesView
import ru.startandroid.develop.moviesexplorer.presentation.poster.PosterView

class PosterPresenter(
    private val view: PosterView,
    private val imageUrl: String,  ) {


    fun onCreate() {
            view.setImage(imageUrl)
    }
}