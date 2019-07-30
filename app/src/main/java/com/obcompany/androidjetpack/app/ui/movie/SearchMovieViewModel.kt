package com.obcompany.androidjetpack.app.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.obcompany.androidjetpack.app.model.SearchMoviesResponse
import com.obcompany.androidjetpack.repository.MovieRepository
import com.obcompany.androidjetpack.app.model.Resource
import com.obcompany.androidjetpack.utility.base.BaseViewModel
import com.obcompany.androidjetpack.utility.Status

class SearchMovieViewModel(private val repository: MovieRepository): BaseViewModel() {
    private val search = MutableLiveData<String>()

    private val _page = MutableLiveData<Int>().apply { value = 1 }
    val page: LiveData<Int> = _page

    private val _movies = MediatorLiveData<SearchMoviesResponse>()
    val movies: LiveData<SearchMoviesResponse> = _movies

    init {
        _movies.addSource(Transformations.switchMap(search){ search -> repository.searchMovies(search, 1) }){ data ->
            _movies.removeSource(movies)
            processResource(data)
            _page.value = 1
        }
    }

    override fun onCleared() {
        super.onCleared()
        repository.clear()
    }

    private fun processResource(resource: Resource<SearchMoviesResponse>){
        when (resource.status) {
            Status.LOADING -> {
                setLoading(true)
            }
            Status.SUCCESS -> {
                setLoading(false)
                resource.data?.let {
                    if(it.results.isNotEmpty()){
                        _movies.value = it
                    }else{
                        _page.value = (_page.value ?: 1) - 1
                    }
                }
            }
            Status.ERROR -> {
                setLoading(false)
            }
        }
    }

    private fun search(query: String = search.value ?: "", page: Int = _page.value ?: 1){
        _movies.removeSource(movies)

        val liveData = repository.searchMovies(query, page)

        _movies.addSource(liveData){ data ->
            processResource(data)
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
            search()
        }
    }

    fun backPage(){
        if(dataIsNotEmpty()){
            val currentPage: Int = _page.value ?: 1
            _page.value = if (currentPage > 1) currentPage - 1 else 1
            search()
        }
    }

    private fun dataIsNotEmpty(): Boolean{
        var boolean = false
        movies.value?.let{
            boolean = it.results.size > 0
        }
        return boolean
    }


}