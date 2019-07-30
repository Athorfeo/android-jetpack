package com.obcompany.androidjetpack.app.ui.movie.detail

import androidx.lifecycle.LiveData
import com.obcompany.androidjetpack.app.model.Movie
import com.obcompany.androidjetpack.repository.MovieRepository
import com.obcompany.androidjetpack.app.model.Resource
import com.obcompany.androidjetpack.utility.base.BaseViewModel

class DetailMovieViewModel(private val repository: MovieRepository): BaseViewModel() {
    fun searchMovie(movieId: Int): LiveData<Resource<Movie>> {
        return  repository.searchMovie(movieId)
    }
}