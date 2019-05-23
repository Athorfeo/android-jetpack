package com.obcompany.androidjetpack.app.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.obcompany.androidjetpack.app.model.SearchMoviesResponse
import com.obcompany.androidjetpack.system.repository.MovieRepository
import com.obcompany.androidjetpack.app.model.Resource
import com.obcompany.androidjetpack.app.viewmodel.BaseViewModel

class SearchMovieViewModel(private val repository: MovieRepository): BaseViewModel() {
    private val search = MutableLiveData<String>()
    private var _movies = MutableLiveData<Resource<SearchMoviesResponse>>()
    val movies: LiveData<Resource<SearchMoviesResponse>> = Transformations.switchMap(search){ search ->
        repository.searchMovies(search)
    }

    fun searchMovies(string: String) {
        if(!string.isEmpty()){
            search.value = string
        }
    }

    override fun onCleared() {
        super.onCleared()
        repository.clear()
    }
}