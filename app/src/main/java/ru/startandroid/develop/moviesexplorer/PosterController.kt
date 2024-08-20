package ru.startandroid.develop.moviesexplorer

import android.app.Activity
import android.widget.ImageView
import com.bumptech.glide.Glide

class PosterController(private val activity: Activity) {

    private lateinit var poster: ImageView

    fun onCreate() {
        poster = activity.findViewById(R.id.poster)
        val url = activity.intent.extras?.getString("poster", "")

        Glide.with(activity.applicationContext)
            .load(url)
            .into(poster)
    }
}