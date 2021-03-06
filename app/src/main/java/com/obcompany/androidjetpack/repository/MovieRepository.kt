package com.obcompany.androidjetpack.repository

import androidx.lifecycle.LiveData
import com.obcompany.androidjetpack.app.model.Movie
import com.obcompany.androidjetpack.app.model.SearchMoviesResponse
import com.obcompany.androidjetpack.api.API
import com.obcompany.androidjetpack.utility.SimpleNetworkBoundResource
import com.obcompany.androidjetpack.app.model.Resource
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import retrofit2.Response

class MovieRepository: BaseRepository(){
    fun searchMovies(search: String, page: Int): LiveData<Resource<SearchMoviesResponse>> {
        return object : SimpleNetworkBoundResource<SearchMoviesResponse>() {
            override fun notifyDisposable(disposable: Disposable) {
                addDisposable(disposable)
            }

            override fun callService(): Observable<Response<SearchMoviesResponse>> {
                return api.searchMovies(API.apiKey, search, page.toString())
            }
        }.asLiveData()
    }

    fun searchMovie(id: Int): LiveData<Resource<Movie>> {
        return object : SimpleNetworkBoundResource<Movie>() {
            override fun notifyDisposable(disposable: Disposable) {
                addDisposable(disposable)
            }

            override fun callService(): Observable<Response<Movie>> {
                return api.searchMovie(id, API.apiKey)
            }
        }.asLiveData()
    }

}