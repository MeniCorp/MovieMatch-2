package com.menicorp.moviematch.ui.likes

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

class LikesFragment : Fragment() {

    private lateinit var likedMoviesRecyclerView: RecyclerView
    private lateinit var emptyStateText: TextView
    private lateinit var adapter: MovieAdapter

    private val likedMovies = mutableListOf<Movie>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_likes, container, false)

        likedMoviesRecyclerView = view.findViewById(R.id.likedMoviesRecyclerView)
        emptyStateText = view.findViewById(R.id.emptyStateText)

        adapter = MovieAdapter(likedMovies, ::onLike, ::onDislike)
        
        likedMoviesRecyclerView.layoutManager = LinearLayoutManager(context)
        likedMoviesRecyclerView.adapter = adapter

        updateEmptyState()

        return view
    }

    private fun onLike(movie: Movie) {
        // Handle like action - already in liked movies list
    }

    private fun onDislike(movie: Movie) {
        // Remove from likes when disliked
        likedMovies.remove(movie)
        adapter.notifyDataSetChanged()
        updateEmptyState()
    }

    private fun updateEmptyState() {
        emptyStateText.visibility = if (likedMovies.isEmpty()) View.VISIBLE else View.GONE
    }

    fun setLikedMovies(movies: List<Movie>) {
        likedMovies.clear()
        likedMovies.addAll(movies)
        adapter.notifyDataSetChanged()
        updateEmptyState()
    }

    companion object {
        fun newInstance(): LikesFragment = LikesFragment()
    }
}
