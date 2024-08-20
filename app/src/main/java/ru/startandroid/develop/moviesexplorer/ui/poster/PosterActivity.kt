package ru.startandroid.develop.moviesexplorer.ui.poster

import android.app.Activity
import android.os.Bundle
import ru.startandroid.develop.moviesexplorer.util.Creator
import ru.startandroid.develop.moviesexplorer.R

class PosterActivity : Activity() {

    private val posterController = Creator.providePosterController(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poster)
        posterController.onCreate()
    }
}