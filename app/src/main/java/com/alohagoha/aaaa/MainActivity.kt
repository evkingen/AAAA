package com.alohagoha.aaaa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alohagoha.aaaa.databinding.ActivityMainBinding
import com.alohagoha.aaaa.ui.FragmentMoviesDetails
import com.alohagoha.aaaa.ui.FragmentMoviesList

class MainActivity : AppCompatActivity() {

    private val onMovieCardClickListener = FragmentMoviesList.OnMovieCardClickListener { movieId ->
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, FragmentMoviesDetails.newInstance(movieId))
            .addToBackStack(null)
            .commit()
    }
    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.setDisplayHomeAsUpEnabled(false)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.fragment_container,
                    FragmentMoviesList().also { it.clickListener = onMovieCardClickListener },
                    FragmentMoviesList.FRAGMENT_TAG
                )
                .commit()
        }
        setContentView(mainBinding.root)
        (supportFragmentManager.findFragmentByTag(FragmentMoviesList.FRAGMENT_TAG) as FragmentMoviesList?)?.clickListener =
            onMovieCardClickListener
    }
}
