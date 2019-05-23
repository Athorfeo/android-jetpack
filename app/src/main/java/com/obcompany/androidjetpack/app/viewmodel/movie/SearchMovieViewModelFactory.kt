package com.obcompany.androidjetpack.app.viewmodel.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.obcompany.androidjetpack.app.ui.movie.SearchMovieViewModel
import com.obcompany.androidjetpack.system.repository.MovieRepository

class SearchMovieViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchMovieViewModel(repository) as T
    }
}