package com.obcompany.androidjetpack.api

import com.obcompany.androidjetpack.app.model.Movie
import com.obcompany.androidjetpack.app.model.SearchMoviesResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET("3/search/movie")
    fun searchMovies(@Query("api_key") apiKey: String,
                     @Query("query") query: String,
                     @Query("page") page: String) : Observable<Response<SearchMoviesResponse>>

    @GET("3/movie/{movie_id}")
    fun searchMovie( @Path("movie_id") movie_id: Int,
                     @Query("api_key") apiKey: String) : Observable<Response<Movie>>
    companion object {
        const val apiKey = "f33b50a94cda8f40e747fbca8ce620f8"
        fun create(): API{
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.themoviedb.org/")
                .build()
            return retrofit.create(API::class.java)
        }
    }

}