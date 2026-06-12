package com.menicorp.moviematch.data.model

data class Movie(
    val id: Long,
    val title: String,
    val posterUrl: String,
    val overview: String,
    val genreIds: List<Int>,
    val releaseYear: Int,
    val cast: List<String>,
    val rating: Double = 0.0
)
