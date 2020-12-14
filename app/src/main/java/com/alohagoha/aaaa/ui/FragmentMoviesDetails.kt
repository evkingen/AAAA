package com.alohagoha.aaaa.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.alohagoha.aaaa.R
import com.alohagoha.aaaa.data.ActorsDataSource
import com.alohagoha.aaaa.databinding.FragmentMoviesDetailsBinding
import com.alohagoha.aaaa.ui.adapters.ActorsListAdapter

class FragmentMoviesDetails : Fragment() {

    private var _moviesDetailsBinding: FragmentMoviesDetailsBinding? = null
    private val moviesDetailsBinding get() = _moviesDetailsBinding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _moviesDetailsBinding = FragmentMoviesDetailsBinding.inflate(inflater, container, false)
        return moviesDetailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()
        initBackButton()
    }

    private fun initBackButton() {
        moviesDetailsBinding.back.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }

    private fun initRv() {
        moviesDetailsBinding.castRv.apply {
            adapter = ActorsListAdapter(ActorsDataSource.getAllData())
            layoutManager =
                    GridLayoutManager(context, resources.getInteger(R.integer.actors_span_count))
            val decorator = DividerItemDecoration(context, GridLayoutManager.HORIZONTAL)
            ContextCompat.getDrawable(context, R.drawable.divider_actors)?.let {
                decorator.setDrawable(it)
            }
            addItemDecoration(decorator)
            setHasFixedSize(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _moviesDetailsBinding = null
    }
}
