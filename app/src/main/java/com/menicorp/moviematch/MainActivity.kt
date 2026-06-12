package com.menicorp.moviematch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.appbar.MaterialToolbar
import com.menicorp.moviematch.data.model.Movie
import com.menicorp.moviematch.ui.main.MovieAdapter
import com.menicorp.moviematch.R
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.coordinatorlayout.widget.CoordinatorLayout

class MainActivity : AppCompatActivity() {

    private lateinit var movieRecyclerView: RecyclerView
    private lateinit var emptyStateText: TextView
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        movieRecyclerView = findViewById(R.id.movieRecyclerView)
        emptyStateText = findViewById(R.id.emptyStateText)

        val movies = getSampleMovies()
        adapter = MovieAdapter(movies, ::onLike, ::onDislike)
        
        movieRecyclerView.layoutManager = LinearLayoutManager(this)
        movieRecyclerView.adapter = adapter

        val bottomAppBar = findViewById<BottomAppBar>(R.id.bottomAppBar)
        bottomAppBar.replaceMenu(R.menu.main_menu)
    }

    private fun getSampleMovies(): List<Movie> {
        return listOf(
            Movie(
                id = 1,
                title = "Inception",
                posterUrl = "",
                overview = "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.",
                genreIds = listOf(12, 18, 878),
                releaseYear = 2010,
                cast = listOf("Leonardo DiCaprio", "Marion Cotillard", "Joseph Gordon-Levitt"),
                rating = 8.8
            ),
            Movie(
                id = 2,
                title = "The Dark Knight",
                posterUrl = "",
                overview = "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.",
                genreIds = listOf(18, 80, 28),
                releaseYear = 2008,
                cast = listOf("Christian Bale", "Heath Ledger", "Aaron Eckhart"),
                rating = 9.0
            ),
            Movie(
                id = 3,
                title = "Interstellar",
                posterUrl = "",
                overview = "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.",
                genreIds = listOf(12, 18, 878),
                releaseYear = 2014,
                cast = listOf("Matthew McConaughey", "Anne Hathaway", "Jessica Chastain"),
                rating = 8.6
            )
        )
    }

    private fun onLike(movie: Movie) {
        // Handle like action - add to likes list
    }

    private fun onDislike(movie: Movie) {
        // Handle dislike action - add to dislikes list
    }
}
