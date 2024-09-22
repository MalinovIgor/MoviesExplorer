package startandroid.develop.moviesexplorer.data

import startandroid.develop.moviesexplorer.data.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response
}