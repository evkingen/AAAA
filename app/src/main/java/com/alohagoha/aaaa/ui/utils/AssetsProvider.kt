package com.alohagoha.aaaa.ui.utils

import android.content.Context
import com.alohagoha.aaaa.data.IAssetsProvider

class AssetsProvider(private val context: Context) : IAssetsProvider {
    override fun readAssetFileToString(fileName: String): String {
        val stream = context.assets.open(fileName)
        return stream.bufferedReader().readText()
    }
}
