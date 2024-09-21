package ru.startandroid.develop.moviesexplorer.domain.impl

import android.util.Log
import ru.startandroid.develop.moviesexplorer.domain.api.MoviesInteractor
import ru.startandroid.develop.moviesexplorer.domain.api.MoviesRepository
import ru.startandroid.develop.moviesexplorer.domain.models.Movie
import ru.startandroid.develop.moviesexplorer.util.Resource
import java.util.concurrent.Executors

class MoviesInteractorImpl(private val repository: MoviesRepository) : MoviesInteractor {

    private val executor = Executors.newCachedThreadPool()

    override fun searchMovies(expression: String, consumer: MoviesInteractor.MoviesConsumer) {
        executor.execute {
            when (val resource = repository.searchMovies(expression)) {
                is Resource.Success -> {
                    consumer.consume(resource.data, null)
                }

                is Resource.Error -> {
                    consumer.consume(resource.data, resource.message)
                }
            }
        }
    }


    override fun getMovieDetails(id: String, consumer: MoviesInteractor.MovieDetailsConsumer) {
        executor.execute {
            Log.d("MoviesInteractor", "Resource state:")

            when (val resource = repository.getMovieDetails(id)) {
                is Resource.Success -> {
                    Log.d("MoviesInteractor", "Resource state: $resource")

                    resource.data?.let { consumer.consume(it, null) }
                }

                is Resource.Error -> {
                    Log.d("MoviesInteractor", "Resource state: $resource")

                    resource.data?.let { consumer.consume(it, resource.message) }
                }
            }
        }
    }
    override fun addMovieToFavorites(movie: Movie) {
        repository.addMovieToFavorites(movie)
    }

    override fun removeMovieFromFavorites(movie: Movie) {
        repository.removeMovieFromFavorites(movie)
    }
}