package com.obcompany.androidjetpack.system.repository

import androidx.lifecycle.LiveData
import com.obcompany.androidjetpack.data.api.ApiService
import com.obcompany.androidjetpack.data.api.model.SearchMoviesResponse
import com.obcompany.androidjetpack.system.utility.SimpleNetworkBoundResource
import com.obcompany.androidjetpack.system.utility.Resource
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import retrofit2.Response

class MovieRepository: BaseRepository(){
    fun searchMovies(search: String): LiveData<Resource<SearchMoviesResponse>> {
        return object : SimpleNetworkBoundResource<SearchMoviesResponse>() {
            override fun notifyDisposable(disposable: Disposable) {
                addDisposable(disposable)
            }

            override fun callService(): Observable<Response<SearchMoviesResponse>> {
                return api.searchMovies(ApiService.apiKey, search, "1")
            }
        }.asLiveData()
    }
}