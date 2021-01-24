package com.alohagoha.aaaa.ui.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.alohagoha.aaaa.ui.FragmentMoviesList

class MoviesFragmentFactory(
    private val onMovieCardClickListener: FragmentMoviesList.OnMovieCardClickListener,
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (loadFragmentClass(classLoader, className)) {
            FragmentMoviesList::class.java -> FragmentMoviesList(onMovieCardClickListener)
            else -> super.instantiate(classLoader, className)
        }
    }
}
