package com.shevy.movies.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shevy.movies.R
import com.shevy.movies.databinding.MovieItemBinding
import com.shevy.movies.model.Movie
import com.squareup.picasso.Picasso

class MovieAdapter(private val movies: ArrayList<Movie>, val context: Context) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = MovieItemBinding.bind(itemView)

        var posterImageView: ImageView = binding.posterImageView
        var titleTextView: TextView = binding.titleTextView
        var yearTextView: TextView = binding.yearTextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentMovie = movies[position]
        val title = currentMovie.title
        val year = currentMovie.year
        val posterUrl = currentMovie.posterUrl

        holder.titleTextView.text = title
        holder.yearTextView.text = year
        Picasso.get().load(posterUrl).fit().centerInside().into(holder.posterImageView)

    }

    override fun getItemCount(): Int {
        return movies.size
    }
}