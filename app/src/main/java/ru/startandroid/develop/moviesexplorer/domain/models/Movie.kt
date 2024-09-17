package ru.startandroid.develop.moviesexplorer.domain.models

data class Movie(
    val id: String,
    val resultType: String,
    val image: String,
    val title: String,
    val description: String,
   // val inFavorite: Boolean,
)
