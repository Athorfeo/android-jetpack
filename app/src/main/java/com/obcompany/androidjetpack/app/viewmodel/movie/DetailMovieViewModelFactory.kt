package com.obcompany.androidjetpack.app.viewmodel.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.obcompany.androidjetpack.app.ui.movie.detail.DetailMovieViewModel
import com.obcompany.androidjetpack.repository.Repository

class DetailMovieViewModelFactory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailMovieViewModel(repository) as T
    }
}