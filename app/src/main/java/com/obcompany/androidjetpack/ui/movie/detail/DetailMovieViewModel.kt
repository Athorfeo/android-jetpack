package com.obcompany.androidjetpack.ui.movie.detail

import androidx.lifecycle.ViewModel
import com.obcompany.androidjetpack.data.api.ApiService
import com.obcompany.androidjetpack.data.api.model.Movie
import com.obcompany.androidjetpack.repository.Repository
import io.reactivex.Observable

class DetailMovieViewModel(private val repository: Repository): ViewModel() {
    fun searchMovie(movieId: Int): Observable<Movie> {
        return repository.getApiService().searchMovie(movieId, ApiService.apiKey)
    }
}