package com.obcompany.androidjetpack.app.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.obcompany.androidjetpack.app.model.SearchMoviesResponse
import com.obcompany.androidjetpack.repository.MovieRepository
import com.obcompany.androidjetpack.app.model.Resource
import com.obcompany.androidjetpack.app.viewmodel.BaseViewModel

class SearchMovieViewModel(private val repository: MovieRepository): BaseViewModel() {
    private val search = MutableLiveData<String>()

    private val _page = MutableLiveData<Int>().apply{value = 1}
    val page: LiveData<Int> = _page

    private val _movies = MediatorLiveData<Resource<SearchMoviesResponse>>()
    val movies: LiveData<Resource<SearchMoviesResponse>> = _movies

    init {
        //Search
        _movies.addSource(Transformations.switchMap(search){ search -> repository.searchMovies(search, 1) }){ data ->
            _movies.value = data
            _page.value = 1
        }
        _movies.addSource(Transformations.switchMap(page){ page -> repository.searchMovies(search.value ?: "", page) }){ data ->
            _movies.value = data
        }
    }

    fun searchMovies(string: String) {
        if(string.isNotEmpty()){
            search.value = string
        }
    }

    fun nextPage(){
        if(dataIsNotEmpty()){
            val currentPage: Int = _page.value ?: 1
            _page.value = currentPage + 1
        }
    }

    fun backPage(){
        if(dataIsNotEmpty()){
            val currentPage: Int = _page.value ?: 1
            _page.value = if (currentPage > 1) currentPage - 1 else 1
        }
    }

    private fun dataIsNotEmpty(): Boolean{
        var boolean = false
        movies.value?.let{
            boolean = it.data?.results?.size ?: 0 > 0
        }
        return boolean
    }

    override fun onCleared() {
        super.onCleared()
        repository.clear()
    }
}