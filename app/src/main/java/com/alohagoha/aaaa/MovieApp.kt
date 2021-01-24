package com.alohagoha.aaaa

import android.app.Application
import com.alohagoha.aaaa.data.IAssetsProvider
import com.alohagoha.aaaa.data.JsonLoader
import com.alohagoha.aaaa.data.MoviesRepository
import com.alohagoha.aaaa.repositories.IMoviesRepository
import com.alohagoha.aaaa.ui.utils.AssetsProvider

class MovieApp : Application() {
    val moviesRepository: IMoviesRepository by lazy { MoviesRepository(jsonLoader) }
    private val jsonLoader: JsonLoader by lazy { JsonLoader(assetsProvider) }
    private val assetsProvider: IAssetsProvider by lazy { AssetsProvider(applicationContext) }
}
