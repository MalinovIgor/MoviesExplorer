package ru.startandroid.develop.moviesexplorer.data.network
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import ru.startandroid.develop.moviesexplorer.data.dto.MovieCastResponse
import ru.startandroid.develop.moviesexplorer.data.dto.MovieDetailsResponse
import ru.startandroid.develop.moviesexplorer.data.dto.MovieSearchResponse

interface IMDbApiService {
    @GET("/en/API/SearchMovie/k_zcuw1ytf/{expression}")

fun searchMovies(@Path("expression") expression: String): Call<MovieSearchResponse>


    @GET("/en/API/Title/k_zcuw1ytf/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: String): Call<MovieDetailsResponse>

    @GET("/en/API/FullCast/YOUR_API_KEY/{movie_id}")
    fun getFullCast(@Path("movie_id") movieId: String): Call<MovieCastResponse>
}