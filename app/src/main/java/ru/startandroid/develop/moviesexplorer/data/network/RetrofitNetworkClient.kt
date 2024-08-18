package ru.startandroid.develop.moviesexplorer.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.startandroid.develop.moviesexplorer.data.NetworkClient
import ru.startandroid.develop.moviesexplorer.data.dto.MoviesSearchRequest
import ru.startandroid.develop.moviesexplorer.data.dto.Response

class RetrofitNetworkClient : NetworkClient {

    private val imdbBaseUrl = "https://tv-api.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(imdbBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val imdbService = retrofit.create(IMDbApiService::class.java)

    override fun doRequest(dto: Any): Response {
        if (dto is MoviesSearchRequest) {
            val call = imdbService.searchMovies(dto.expression)
            try {
                val response = call.execute()
                val body = response.body() ?: Response()
                return body.apply { resultCode = response.code() }
            } catch (e: Exception) {
                // Обработать исключение, включая SocketTimeoutException
                // Например, вернуть пустой Response с кодом ошибки
                return Response().apply { resultCode = 500 } // Установите нужный код ошибки
            }
        } else {
            return Response().apply { resultCode = 400 }
        }
    }
}