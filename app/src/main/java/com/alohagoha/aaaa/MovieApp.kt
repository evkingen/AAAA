package com.alohagoha.aaaa

import android.app.Application
import com.alohagoha.aaaa.data.JsonLoader

class MovieApp : Application() {
    val jsonLoader: JsonLoader by lazy { JsonLoader(applicationContext) }
}
