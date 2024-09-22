package ru.startandroid.develop.moviesexplorer.ui.cast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import ru.startandroid.develop.moviesexplorer.R
import ru.startandroid.develop.moviesexplorer.databinding.ActivityMoviesCastBinding

class MoviesCastActivity:AppCompatActivity(R.layout.activity_movies_cast) {

    private lateinit var binding : ActivityMoviesCastBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesCastBinding.inflate(layoutInflater)
        setContentView(binding.root)



        // TODO "Добавить вёрстку"
        // TODO "Прочитать идентификатор фильма из Intent"
    }




    companion object{
        private const val ARGS_MOVIE_ID = "movie_id"

        fun newInstance(context: Context, movieId:String):Intent{
            return Intent(context, MoviesCastActivity::class.java).apply {
                putExtra(ARGS_MOVIE_ID, movieId)
            }
        }
    }
}