package startandroid.develop.moviesexplorer.di

import android.content.Context
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import startandroid.develop.moviesexplorer.data.NetworkClient
import startandroid.develop.moviesexplorer.data.network.IMDbApiService
import startandroid.develop.moviesexplorer.data.network.RetrofitNetworkClient

val dataModule = module {

    single {
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }).build()
    }

    single<IMDbApiService> {
        Retrofit.Builder()
            .baseUrl("https://imdb-api.com")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IMDbApiService::class.java)
    }

    single {
        androidContext()
            .getSharedPreferences("local_storage", Context.MODE_PRIVATE)
    }

    factory { Gson() }


    single<NetworkClient> {
        RetrofitNetworkClient(get(), androidContext())
    }

}