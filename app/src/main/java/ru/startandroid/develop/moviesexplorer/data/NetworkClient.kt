package ru.startandroid.develop.moviesexplorer.data

import ru.startandroid.develop.moviesexplorer.data.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response
}