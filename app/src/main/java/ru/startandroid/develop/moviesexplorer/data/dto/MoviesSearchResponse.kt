package ru.startandroid.develop.moviesexplorer.data.dto

import ru.startandroid.develop.moviesexplorer.domain.models.Movie

class MoviesSearchResponse(val searchType: String,
                           val expression: String,
                           val results: List<MovieDto>) : Response()
