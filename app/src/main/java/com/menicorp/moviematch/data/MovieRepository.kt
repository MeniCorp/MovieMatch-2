package com.menicorp.moviematch.data

import com.menicorp.moviematch.data.model.Movie

object MovieRepository {
    private val likedMovies = mutableListOf<Movie>()
    private val dislikedMovies = mutableListOf<Movie>()

    fun addLike(movie: Movie) {
        if (likedMovies.none { it.id == movie.id }) {
            likedMovies.add(movie)
        }
    }

    fun addDislike(movie: Movie) {
        if (dislikedMovies.none { it.id == movie.id }) {
            dislikedMovies.add(movie)
        }
    }

    fun getLikedMovies(): List<Movie> = likedMovies.toList()

    fun getDislikedMovies(): List<Movie> = dislikedMovies.toList()

    fun removeLike(movie: Movie) {
        likedMovies.removeAll { it.id == movie.id }
    }
}
