package com.obcompany.androidjetpack.app.ui.movie

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.obcompany.androidjetpack.data.api.model.SearchedMovie

class SearchMovieAdapterViewModel(movie: SearchedMovie?): ViewModel() {
    private val movie = checkNotNull(movie)

    val titleString = ObservableField<String>(this.movie.title)
}