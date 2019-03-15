package com.obcompany.androidjetpack.utilities

import com.obcompany.androidjetpack.repository.Repository
import com.obcompany.androidjetpack.ui.movie.detail.DetailMovieViewModelFactory
import com.obcompany.androidjetpack.ui.movie.search.SearchMovieViewModelFactory

object InjectionUtil {
    fun provideSearchMovieViewModelFactory(): SearchMovieViewModelFactory {
        val repository = Repository.instance()
        return SearchMovieViewModelFactory(repository)
    }

    fun provideDetailMovieViewModelFactory(): DetailMovieViewModelFactory {
        val repository = Repository.instance()
        return DetailMovieViewModelFactory(repository)
    }
}