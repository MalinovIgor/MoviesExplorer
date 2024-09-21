package ru.startandroid.develop.moviesexplorer

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.startandroid.develop.moviesexplorer.domain.api.MoviesInteractor
import ru.startandroid.develop.moviesexplorer.domain.models.MovieDetails
import ru.startandroid.develop.moviesexplorer.ui.movies.models.DetailsState
import ru.startandroid.develop.moviesexplorer.ui.movies.models.MoviesState

class MovieDetailsViewModel(
    private val movieId: String,
    private val moviesInteractor: MoviesInteractor,
) : ViewModel() {
    private val stateLiveData = MutableLiveData<DetailsState>()
    fun observeState(): LiveData<DetailsState> = stateLiveData

    init {
        moviesInteractor.getMovieDetails(movieId, object : MoviesInteractor.MovieDetailsConsumer {

            override fun consume(movieDetails: MovieDetails?, errorMessage: String?) {
                Log.d("MovieDetailsViewModel", "consume called with movieDetails: $movieDetails, error: $errorMessage")
                if (movieDetails != null) {
                    stateLiveData.postValue(DetailsState.Details(movieDetails))
                } else {
                    stateLiveData.postValue(DetailsState.Error(errorMessage ?: "Unknown error"))
                }
            }
        })
    }
}