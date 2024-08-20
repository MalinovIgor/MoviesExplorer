package ru.startandroid.develop.moviesexplorer

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.startandroid.develop.moviesexplorer.domain.api.MoviesInteractor
import ru.startandroid.develop.moviesexplorer.domain.models.Movie
import ru.startandroid.develop.moviesexplorer.presentation.movies.MoviesView
import ru.startandroid.develop.moviesexplorer.ui.movies.MoviesAdapter
import ru.startandroid.develop.moviesexplorer.util.Creator

class MoviesSearchPresenter(
    private val view: MoviesView,
    private val context : Context,
    private val adapter: MoviesAdapter,
) {
    private val movies = ArrayList<Movie>()
    private val moviesInteractor = Creator.provideMoviesInteractor(context)
    private val handler = Handler(Looper.getMainLooper())

    fun searchDebounce(changedText: String) {
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)

        val searchRunnable = Runnable { searchRequest(changedText) }

        val postTime = SystemClock.uptimeMillis() + SEARCH_DEBOUNCE_DELAY
        handler.postAtTime(
            searchRunnable,
            SEARCH_REQUEST_TOKEN,
            postTime,
        )
    }
    fun onCreate() {
        adapter.movies = movies
    }
    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            // Заменили работу с элементами UI на
            // вызовы методов интерфейса MoviesView
            view.showPlaceholderMessage(false)
            view.showMoviesList(false)
            view.showProgressBar(true)

            moviesInteractor.searchMovies(newSearchText, object : MoviesInteractor.MoviesConsumer {
                override fun consume(foundMovies: List<Movie>?, errorMessage: String?) {
                    handler.post {
                        view.showProgressBar(false)
                        if (foundMovies != null) {
                            movies.clear()
                            movies.addAll(foundMovies)
                            adapter.notifyDataSetChanged()
                            // Заменили работу с элементами UI на
                            // вызовы методов интерфейса MoviesView
                            view.showMoviesList(true)
                        }
                        if (errorMessage != null) {
                            showMessage(context.getString(R.string.something_went_wrong), errorMessage)
                        } else if (movies.isEmpty()) {
                            showMessage(context.getString(R.string.nothing_found), "")
                        } else {
                            hideMessage()
                        }
                    }
                }
            })
        }
    }


    fun onDestroy() {
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
    }

    private fun showMessage(text: String, additionalMessage: String) {
        if (text.isNotEmpty()) {
            // Заменили работу с элементами UI на
            // вызовы методов интерфейса
            view.showPlaceholderMessage(true)
            movies.clear()
            adapter.notifyDataSetChanged()
            view.changePlaceholderText(text)
            if (additionalMessage.isNotEmpty()) {
                Toast.makeText(context, additionalMessage, Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            // Заменили работу с элементами UI на
            // вызовы методов интерфейса
            view.showPlaceholderMessage(false)
        }
    }

    private fun hideMessage() {
        // Заменили работу с элементами UI на
        // вызовы методов интерфейса
        view.showPlaceholderMessage(false)
    }



    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private val SEARCH_REQUEST_TOKEN = Any()
    }
}