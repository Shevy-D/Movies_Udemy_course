package com.shevy.movies.acitivities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.shevy.movies.data.MovieAdapter
import com.shevy.movies.databinding.ActivityMainBinding
import com.shevy.movies.model.Movie
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var recyclerView: RecyclerView
    lateinit var movieAdapter: MovieAdapter
    lateinit var movies: ArrayList<Movie>
    lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerView
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(this)

        movies = ArrayList()
        requestQueue = Volley.newRequestQueue(this)

        getMovies()

/*        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MovieAdapter(movies, this@MainActivity)
        }*/
    }

    private fun getMovies() {
        val url = "https://www.omdbapi.com/?apikey=a19710&s=superman"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response: JSONObject ->
                try {
                    val jsonArray = response.getJSONArray("Search")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val title = jsonObject.getString("Title")
                        val year = jsonObject.getString("Year")
                        val posterUrl = jsonObject.getString("Poster")
                        val movie = Movie()
                        movie.title = title
                        movie.year = year
                        movie.posterUrl = posterUrl
                        movies.add(movie)
                    }
                    movieAdapter = MovieAdapter(movies, this@MainActivity )
                    recyclerView.adapter = movieAdapter
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        ) { error: VolleyError -> error.printStackTrace() }
        requestQueue.add(request)
    }
}