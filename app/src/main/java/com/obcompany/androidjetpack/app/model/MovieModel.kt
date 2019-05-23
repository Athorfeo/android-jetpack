package com.obcompany.androidjetpack.app.model

import com.google.gson.annotations.SerializedName

data class SearchMoviesResponse(
    val page: Int,
    val results: MutableList<SearchedMovie>,
    val total_results: Int,
    val total_pages: Int
)

data class SearchedMovie(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String
)

data class Movie(
    @SerializedName("id") val id: Int,
    @SerializedName("backdrop_path") val imagePath: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("overview") val overview: String
)