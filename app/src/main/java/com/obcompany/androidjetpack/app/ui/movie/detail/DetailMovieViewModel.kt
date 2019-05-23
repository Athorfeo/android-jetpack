package com.obcompany.androidjetpack.app.ui.movie.detail

import androidx.lifecycle.LiveData
import com.obcompany.androidjetpack.app.model.Movie
import com.obcompany.androidjetpack.system.repository.MovieRepository
import com.obcompany.androidjetpack.utility.Resource
import com.obcompany.androidjetpack.utility.BaseViewModel

class DetailMovieViewModel(private val repository: MovieRepository): BaseViewModel() {
    fun searchMovie(movieId: Int): LiveData<Resource<Movie>> {
        return  repository.searchMovie(movieId)
    }
}