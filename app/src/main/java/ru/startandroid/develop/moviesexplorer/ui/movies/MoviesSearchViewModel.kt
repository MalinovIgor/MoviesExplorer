package ru.startandroid.develop.moviesexplorer.ui.movies

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ru.startandroid.develop.moviesexplorer.R
import ru.startandroid.develop.moviesexplorer.SingleLiveEvent
import ru.startandroid.develop.moviesexplorer.domain.api.MoviesInteractor
import ru.startandroid.develop.moviesexplorer.domain.models.Movie
import ru.startandroid.develop.moviesexplorer.ui.movies.models.MoviesState
import ru.startandroid.develop.moviesexplorer.creator.Creator

class MoviesSearchViewModel(application: Application) : AndroidViewModel(application) {

    private val moviesInteractor = Creator.provideMoviesInteractor(getApplication())
    private val handler = Handler(Looper.getMainLooper())

    private val stateLiveData = MutableLiveData<MoviesState>()
    fun observeState(): LiveData<MoviesState> = mediatorStateLiveData

    private val showToast = SingleLiveEvent<String>()
    fun observeShowToast(): LiveData<String> = showToast

    private var latestSearchText: String? = null

    override fun onCleared() {
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
    }
    fun toggleFavorite(movie: Movie) {
        if (movie.inFavorite) {
            moviesInteractor.removeMovieFromFavorites(movie)
        } else {
            moviesInteractor.addMovieToFavorites(movie)
        }
        updateMovieContent(movie.id, movie.copy(inFavorite = !movie.inFavorite))
    }

    private val mediatorStateLiveData = MediatorLiveData<MoviesState>().also { liveData ->
        // 1
        liveData.addSource(stateLiveData) { movieState ->
            liveData.value = when (movieState) {
                // 2
                is MoviesState.Content -> MoviesState.Content(movieState.movies.sortedByDescending { it.inFavorite })
                is MoviesState.Empty -> movieState
                is MoviesState.Error -> movieState
                is MoviesState.Loading -> movieState
                else -> {movieState}
            }
        }
    }
    private fun updateMovieContent(movieId: String, newMovie: Movie) {
        val currentState = stateLiveData.value

        // 2
        if (currentState is MoviesState.Content) {
            // 3
            val movieIndex = currentState.movies.indexOfFirst { it.id == movieId }

            // 4
            if (movieIndex != -1) {
                // 5
                stateLiveData.value = MoviesState.Content(
                    currentState.movies.toMutableList().also {
                        it[movieIndex] = newMovie
                    }
                )
            }
        }
    }
    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }

        this.latestSearchText = changedText
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
            renderState(MoviesState.Loading)

            moviesInteractor.searchMovies(newSearchText, object : MoviesInteractor.MoviesConsumer {
                override fun consume(foundMovies: List<Movie>?, errorMessage: String?) {
                    val movies = mutableListOf<Movie>()
                    if (foundMovies != null) {
                        movies.addAll(foundMovies)
                    }

                    when {
                        errorMessage != null -> {
                            renderState(
                                MoviesState.Error(
                                    getApplication<Application>().getString(R.string.something_went_wrong),
                                )
                            )
                            showToast.postValue(errorMessage!!)
                        }

                        movies.isEmpty() -> {
                            renderState(
                                MoviesState.Empty(
                                    getApplication<Application>().getString(R.string.nothing_found),
                                )
                            )
                        }

                        else -> {
                            renderState(
                                MoviesState.Content(
                                    movies = movies,
                                )
                            )
                        }
                    }

                }
            })
        }
    }

    private fun renderState(state: MoviesState) {
        stateLiveData.postValue(state)
    }


    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private val SEARCH_REQUEST_TOKEN = Any()

        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MoviesSearchViewModel(this[APPLICATION_KEY] as Application)
            }
        }
    }
}