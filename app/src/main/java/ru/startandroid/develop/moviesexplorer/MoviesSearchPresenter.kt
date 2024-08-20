package ru.startandroid.develop.moviesexplorer

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.widget.Toast
import ru.startandroid.develop.moviesexplorer.domain.api.MoviesInteractor
import ru.startandroid.develop.moviesexplorer.domain.models.Movie
import ru.startandroid.develop.moviesexplorer.presentation.movies.MoviesView
import ru.startandroid.develop.moviesexplorer.util.Creator

class MoviesSearchPresenter(
    private val view: MoviesView,
    private val context : Context,
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
                            view.updateMoviesList(movies)
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
            view.updateMoviesList(movies)
            view.changePlaceholderText(text)
            if (additionalMessage.isNotEmpty()) {
                view.showToast(additionalMessage)
//                Toast.makeText(context, additionalMessage, Toast.LENGTH_LONG)
//                    .show()
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