package com.menicorp.moviematch.ui.suggestions

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

class SuggestionsFragment : Fragment() {

    private lateinit var suggestionsRecyclerView: RecyclerView
    private lateinit var emptyStateText: TextView
    private lateinit var adapter: MovieAdapter

    private val suggestions = mutableListOf<Movie>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_suggestions, container, false)

        suggestionsRecyclerView = view.findViewById(R.id.suggestionsRecyclerView)
        emptyStateText = view.findViewById(R.id.emptyStateText)

        adapter = MovieAdapter(suggestions, ::onLike, ::onDislike)
        
        suggestionsRecyclerView.layoutManager = LinearLayoutManager(context)
        suggestionsRecyclerView.adapter = adapter

        updateEmptyState()

        return view
    }

    private fun onLike(movie: Movie) {
        // Handle like action - add to liked movies
    }

    private fun onDislike(movie: Movie) {
        // Remove from suggestions when disliked
        suggestions.remove(movie)
        adapter.notifyDataSetChanged()
        updateEmptyState()
    }

    private fun updateEmptyState() {
        emptyStateText.visibility = if (suggestions.isEmpty()) View.VISIBLE else View.GONE
    }

    fun setSuggestions(movies: List<Movie>) {
        suggestions.clear()
        suggestions.addAll(movies)
        adapter.notifyDataSetChanged()
        updateEmptyState()
    }

    companion object {
        fun newInstance(): SuggestionsFragment = SuggestionsFragment()
    }
}
