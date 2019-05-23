package com.obcompany.androidjetpack.system.api.service

import com.obcompany.androidjetpack.app.model.Movie
import com.obcompany.androidjetpack.app.model.SearchMoviesResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService{
    @GET("3/search/movie")
    fun searchMovies(@Query("api_key") apiKey: String,
                     @Query("query") query: String,
                     @Query("page") page: String) : Observable<Response<SearchMoviesResponse>>

    @GET("3/movie/{movie_id}")
    fun searchMovie(@Path("movie_id") movie_id: Int,
                    @Query("api_key") apiKey: String) : Observable<Movie>
}