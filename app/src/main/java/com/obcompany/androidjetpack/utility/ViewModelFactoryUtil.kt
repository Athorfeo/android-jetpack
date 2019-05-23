package com.obcompany.androidjetpack.utility

import com.obcompany.androidjetpack.app.viewmodel.movie.DetailMovieViewModelFactory
import com.obcompany.androidjetpack.app.viewmodel.movie.SearchMovieViewModelFactory
import com.obcompany.androidjetpack.system.repository.MovieRepository

object ViewModelFactoryUtil {
    fun provideSearchMovieFactory(): SearchMovieViewModelFactory {
        val repository = MovieRepository()
        return SearchMovieViewModelFactory(repository)
    }

    fun provideDetailMovieFactory(): DetailMovieViewModelFactory {
        val repository = MovieRepository()
        return DetailMovieViewModelFactory(repository)
    }
}