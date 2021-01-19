package com.alohagoha.aaaa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import com.alohagoha.aaaa.databinding.ActivityMainBinding
import com.alohagoha.aaaa.ui.FragmentMoviesDetails
import com.alohagoha.aaaa.ui.FragmentMoviesList
import com.alohagoha.aaaa.ui.utils.MoviesFragmentFactory

class MainActivity : AppCompatActivity() {

    private val onMovieCardClickListener = FragmentMoviesList.OnMovieCardClickListener { movieId ->
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, FragmentMoviesDetails.newInstance(movieId))
            .addToBackStack(null)
            .commit()
    }
    private val fragmentFactory = MoviesFragmentFactory(onMovieCardClickListener)
    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add<FragmentMoviesList>(R.id.fragment_container)
                .commit()
        }
        setContentView(mainBinding.root)
    }
}
