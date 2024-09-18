package ru.startandroid.develop.moviesexplorer.ui.poster

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.startandroid.develop.moviesexplorer.domain.api.MoviesInteractor
import ru.startandroid.develop.moviesexplorer.domain.models.MovieDetails
import ru.startandroid.develop.moviesexplorer.ui.movies.models.DetailsState
import ru.startandroid.develop.moviesexplorer.ui.movies.models.PosterState

class PosterViewModel(private val posterUrl: String) : ViewModel()  {

    private val urlLiveData = MutableLiveData(posterUrl)
    fun observeUrl(): LiveData<String> = urlLiveData

}