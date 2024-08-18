package ru.startandroid.develop.moviesexplorer.data.network
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path
import ru.startandroid.develop.moviesexplorer.data.dto.MoviesSearchResponse

interface IMDbApiService {
    @GET("/en/API/SearchMovie/k_zcuw1ytf/{expression}")

fun searchMovies(@Path("expression") expression: String): Call<MoviesSearchResponse>

}