package com.obcompany.androidjetpack.app.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.obcompany.androidjetpack.app.model.SearchMoviesResponse
import com.obcompany.androidjetpack.system.repository.MovieRepository
import com.obcompany.androidjetpack.app.model.Resource
import com.obcompany.androidjetpack.app.viewmodel.BaseViewModel

class SearchMovieViewModel(private val repository: MovieRepository): BaseViewModel() {
    private val search = MutableLiveData<String>()
    private val page = MutableLiveData<Int>().apply{ value = 1}

    private val _movies = MediatorLiveData<Resource<SearchMoviesResponse>>()
    val movies: LiveData<Resource<SearchMoviesResponse>> = _movies

    init {
        //Search
        _movies.addSource(Transformations.switchMap(search){ search -> repository.searchMovies(search, page.value ?: 1) }){ data ->
            _movies.setValue(data)
        }
    }

    /*val movies: LiveData<Resource<SearchMoviesResponse>> = Transformations.switchMap(search){ search ->
        repository.searchMovies(search)
    }*/

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