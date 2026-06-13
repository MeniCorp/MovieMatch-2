package com.menicorp.moviematch.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.menicorp.moviematch.R
import com.menicorp.moviematch.data.model.Movie
import com.menicorp.moviematch.ui.main.MovieAdapter
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView

class HomeFragment : Fragment() {

    private lateinit var homeRecyclerView: RecyclerView
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
        
        homeRecyclerView.layoutManager = LinearLayoutManager(context)
        homeRecyclerView.adapter = adapter

        updateEmptyState()

        return view
    }

    private fun onLike(movie: Movie) {
        // Handle like action - add to liked movies
    }

    private fun onDislike(movie: Movie) {
        // Remove from home when disliked
        movies.remove(movie)
        adapter.notifyDataSetChanged()
        updateEmptyState()
    }

    private fun updateEmptyState() {
        emptyStateText.visibility = if (movies.isEmpty()) View.VISIBLE else View.GONE
    }

    fun setMovies(movies: List<Movie>) {
        this.movies.clear()
        this.movies.addAll(movies)
        adapter.notifyDataSetChanged()
        updateEmptyState()
    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}
