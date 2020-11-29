package com.alohagoha.aaaa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.alohagoha.aaaa.data.ActorsDataSource
import com.alohagoha.aaaa.databinding.ActivityMainBinding
import com.alohagoha.aaaa.ui.adapters.ActorsListAdapter

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.setDisplayHomeAsUpEnabled(false)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        initRv()

        setContentView(mainBinding.root)
    }

    private fun initRv() {
        mainBinding.castRv.apply {
            adapter = ActorsListAdapter(ActorsDataSource.getAllData())
            layoutManager = GridLayoutManager(context, 4)
            val decorator = DividerItemDecoration(context, GridLayoutManager.HORIZONTAL)
            ContextCompat.getDrawable(context, R.drawable.divider_actors)?.let {
                decorator.setDrawable(it)
            }
            addItemDecoration(decorator)
            setHasFixedSize(true)
        }
    }
}

