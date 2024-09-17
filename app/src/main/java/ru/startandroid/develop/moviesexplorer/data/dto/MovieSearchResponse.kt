package ru.startandroid.develop.moviesexplorer.data.dto

class MovieSearchResponse(val searchType: String,
                          val expression: String,
                          val results: List<MovieDto>) : Response()
