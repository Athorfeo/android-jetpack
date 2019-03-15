package com.obcompany.androidjetpack.ui.movie.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.obcompany.androidjetpack.repository.Repository

class SearchMovieViewModelFactory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchMovieViewModel(repository) as T
    }
}