package ru.startandroid.develop.moviesexplorer.ui.poster

import android.app.Activity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import ru.startandroid.develop.moviesexplorer.PosterPresenter
import ru.startandroid.develop.moviesexplorer.util.Creator
import ru.startandroid.develop.moviesexplorer.R
import ru.startandroid.develop.moviesexplorer.presentation.poster.PosterView

class PosterActivity : Activity(), PosterView {

    private lateinit var posterPresenter: PosterPresenter
    private lateinit var poster: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poster)

        val url = intent.extras?.getString("poster", "") ?: ""
        posterPresenter = Creator.providePosterPresenter(this, url)
        poster = findViewById(R.id.poster)
        posterPresenter.onCreate()
    }
    override fun setImage(url: String) {
        Glide.with(applicationContext)
            .load(url)
            .into(poster)
    }

}