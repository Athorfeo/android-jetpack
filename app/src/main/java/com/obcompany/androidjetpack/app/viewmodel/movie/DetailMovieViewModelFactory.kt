package com.obcompany.androidjetpack.app.viewmodel.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.obcompany.androidjetpack.app.ui.movie.detail.DetailMovieViewModel
import com.obcompany.androidjetpack.repository.MovieRepository

class DetailMovieViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailMovieViewModel(repository) as T
    }
}