package com.obcompany.androidjetpack.utilities

import com.obcompany.androidjetpack.repository.Repository
import com.obcompany.androidjetpack.app.viewmodel.movie.DetailMovieViewModelFactory
import com.obcompany.androidjetpack.app.viewmodel.movie.SearchMovieViewModelFactory
import com.obcompany.androidjetpack.system.repository.MovieRepository

object InjectionUtil {
    fun provideSearchMovieViewModelFactory(): SearchMovieViewModelFactory {
        val repository = MovieRepository()
        return SearchMovieViewModelFactory(repository)
    }

    fun provideDetailMovieViewModelFactory(): DetailMovieViewModelFactory {
        val repository = Repository.instance()
        return DetailMovieViewModelFactory(repository)
    }
}