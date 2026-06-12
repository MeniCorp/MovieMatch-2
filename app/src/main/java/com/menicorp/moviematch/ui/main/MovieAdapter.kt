package com.menicorp.moviematch.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.menicorp.moviematch.R
import com.menicorp.moviematch.data.model.Movie
import com.squareup.picasso.Picasso
import android.widget.ImageView
import android.widget.TextView
import android.widget.RatingBar

class MovieAdapter(
    private var movies: List<Movie>,
    private val onLike: (Movie) -> Unit,
    private val onDislike: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val posterImage: ImageView = itemView.findViewById(R.id.posterImage)
        val titleText: TextView = itemView.findViewById(R.id.titleText)
        val overviewText: TextView = itemView.findViewById(R.id.overviewText)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        
        holder.titleText.text = movie.title
        holder.overviewText.text = movie.overview
        holder.ratingBar.rating = movie.rating.toFloat()
        
        if (movie.posterUrl.isNotEmpty()) {
            Picasso.get()
                .load(movie.posterUrl)
                .placeholder(R.drawable.placeholder)
                .into(holder.posterImage)
        }
    }

    override fun getItemCount(): Int = movies.size

    fun updateMovies(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }
}
