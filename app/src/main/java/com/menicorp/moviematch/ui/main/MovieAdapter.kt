package com.menicorp.moviematch.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
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
        val likeIndicator: TextView = itemView.findViewById(R.id.likeIndicator)
        val dislikeIndicator: TextView = itemView.findViewById(R.id.dislikeIndicator)
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
        } else {
            holder.posterImage.setImageResource(R.drawable.placeholder)
        }

        resetSwipeIndicators(holder)
    }

    override fun getItemCount(): Int = movies.size

    fun updateMovies(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    fun getMovieAt(position: Int): Movie = movies[position]

    fun removeMovieAt(position: Int): Movie {
        val movie = movies[position]
        movies = movies.toMutableList().also { it.removeAt(position) }
        notifyItemRemoved(position)
        return movie
    }

    fun showSwipeIndicators(position: Int, progress: Float, recyclerView: RecyclerView? = null) {
        if (position !in 0 until itemCount) return
        recyclerView?.let { recycler ->
            for (i in 0 until recycler.childCount) {
                val child = recycler.getChildAt(i)
                val vh = recycler.getChildViewHolder(child) as? MovieViewHolder
                if (vh?.bindingAdapterPosition == position) {
                    if (progress > 0) {
                        vh.likeIndicator.visibility = View.VISIBLE
                        vh.likeIndicator.alpha = Math.min(1f, progress)
                        vh.dislikeIndicator.visibility = View.GONE
                    } else if (progress < 0) {
                        vh.dislikeIndicator.visibility = View.VISIBLE
                        vh.dislikeIndicator.alpha = Math.min(1f, -progress)
                        vh.likeIndicator.visibility = View.GONE
                    }
                    return
                }
            }
        }
    }

    fun resetSwipeIndicators(holder: RecyclerView.ViewHolder) {
        val vh = holder as MovieViewHolder
        vh.likeIndicator.visibility = View.GONE
        vh.dislikeIndicator.visibility = View.GONE
        vh.cardView.translationX = 0f
    }

    fun animateCardOffScreen(holder: RecyclerView.ViewHolder, dX: Float) {
        val vh = holder as MovieViewHolder
        val screenWidth = vh.cardView.resources.displayMetrics.widthPixels
        val targetX = if (dX > 0) screenWidth.toFloat() else -screenWidth.toFloat()
        ViewCompat.animate(vh.cardView)
            .translationX(targetX)
            .alpha(0f)
            .setDuration(200)
            .withEndAction {
                vh.cardView.translationX = 0f
                vh.cardView.alpha = 1f
            }
            .start()
    }
}
