package com.alohagoha.aaaa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alohagoha.aaaa.databinding.ActivityMainBinding
import com.alohagoha.aaaa.ui.FragmentMoviesDetails
import com.alohagoha.aaaa.ui.FragmentMoviesList

class MainActivity : AppCompatActivity(), FragmentMoviesList.OnMovieCardClickListener {

    lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.setDisplayHomeAsUpEnabled(false)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, FragmentMoviesList())
                    .commit()
        }
        setContentView(mainBinding.root)
    }

    override fun onClickMovie() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FragmentMoviesDetails())
                .addToBackStack(null)
                .commit()
    }

}
