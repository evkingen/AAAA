package com.alohagoha.aaaa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.alohagoha.aaaa.databinding.ActivityMainBinding
import com.alohagoha.aaaa.ui.FragmentMoviesDetails
import com.alohagoha.aaaa.ui.FragmentMoviesList
import com.alohagoha.aaaa.ui.utils.MoviesFragmentFactory

class MainActivity : AppCompatActivity() {


    private val fragmentFactory = MoviesFragmentFactory(::showMovieDetails)
    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add<FragmentMoviesList>(R.id.fragment_container)
            }
        }
        setContentView(mainBinding.root)
    }

    private fun showMovieDetails(movieId: Int) {
        supportFragmentManager.commit {
            replace(R.id.fragment_container, FragmentMoviesDetails.newInstance(movieId))
            addToBackStack(null)
        }
    }
}
