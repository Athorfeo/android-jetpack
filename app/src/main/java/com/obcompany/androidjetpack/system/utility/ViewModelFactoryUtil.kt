package com.obcompany.androidjetpack.system.utility

import com.obcompany.androidjetpack.app.viewmodel.movie.DetailMovieViewModelFactory
import com.obcompany.androidjetpack.app.viewmodel.movie.SearchMovieViewModelFactory
import com.obcompany.androidjetpack.repository.Repository
import com.obcompany.androidjetpack.system.repository.MovieRepository

object ViewModelFactoryUtil {
    fun provideSearchMovieFactory(): SearchMovieViewModelFactory {
        val repository = MovieRepository()
        return SearchMovieViewModelFactory(repository)
    }

    fun provideDetailMovieFactory(): DetailMovieViewModelFactory {
        val repository = Repository.instance()
        return DetailMovieViewModelFactory(repository)
    }
}