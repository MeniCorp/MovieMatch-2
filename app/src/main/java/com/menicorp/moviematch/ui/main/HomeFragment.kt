package com.menicorp.moviematch.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.menicorp.moviematch.R
import com.menicorp.moviematch.data.model.Movie
import com.menicorp.moviematch.ui.main.MovieAdapter
import com.menicorp.moviematch.ui.main.TinderStackRecyclerView
import android.widget.TextView

class HomeFragment : Fragment() {

    private lateinit var homeRecyclerView: TinderStackRecyclerView
    private lateinit var emptyStateText: TextView
    private lateinit var adapter: MovieAdapter

    private val movies = mutableListOf<Movie>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        homeRecyclerView = view.findViewById(R.id.homeRecyclerView)
        emptyStateText = view.findViewById(R.id.emptyStateText)

        adapter = MovieAdapter(movies, ::onLike, ::onDislike)
        
        homeRecyclerView.adapter = adapter

        setupSwipeListener()

        updateEmptyState()

        return view
    }

    private fun setupSwipeListener() {
        homeRecyclerView.setOnSwipeListener(object : TinderStackRecyclerView.OnSwipeListener {
            override fun onLiked(position: Int) {
                onLike(adapter.getMovieAt(position))
            }

            override fun onDisliked(position: Int) {
                onDislike(adapter.getMovieAt(position))
            }

            override fun onSwipeProgress(position: Int, progress: Float) {
                if (adapter.itemCount > 0) {
                    adapter.showSwipeIndicators(position, progress, homeRecyclerView)
                }
            }
        })
    }

    private fun onLike(_movie: Movie) {
        // Handle like action - add to liked movies
    }

    private fun onDislike(_movie: Movie) {
        // Remove from home when disliked
        movies.remove(_movie)
        adapter.updateMovies(movies)
        updateEmptyState()
    }

    private fun updateEmptyState() {
        emptyStateText.visibility = if (movies.isEmpty()) View.VISIBLE else View.GONE
    }

    fun setMovies(movies: List<Movie>) {
        this.movies.clear()
        this.movies.addAll(movies)
        adapter.updateMovies(movies)
        updateEmptyState()
    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}
