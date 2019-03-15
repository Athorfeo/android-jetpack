package com.obcompany.androidjetpack.ui.movie.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.obcompany.androidjetpack.data.api.ApiService
import com.obcompany.androidjetpack.data.api.model.SearchedMovie
import com.obcompany.androidjetpack.repository.Repository
import com.obcompany.androidjetpack.utilities.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchMovieViewModel(private val repository: Repository): BaseViewModel() {
    private val isSearching = MutableLiveData<Boolean>().apply { value = false }
    private val search = MutableLiveData<String>()

    val movies : LiveData<List<SearchedMovie>> = Transformations.switchMap(search){search ->
        val data = MutableLiveData<List<SearchedMovie>>()

        val disposable = repository.getApiService().searchMovies(ApiService.apiKey, search, "1")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    isSearching.value = false
                    data.value = result.results
                },
                {}
            )

        addDisposable(disposable)

        data as LiveData<List<SearchedMovie>>
    }

    fun isSearching(): LiveData<Boolean>{
        return isSearching
    }

    fun searchMovies(string: String) {
        if(!string.isEmpty()){
            isSearching.value = true
            search.value = string
        }
    }
}