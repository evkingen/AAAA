package com.alohagoha.aaaa.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.alohagoha.aaaa.R
import com.alohagoha.aaaa.data.ActorsDataSource
import com.alohagoha.aaaa.databinding.FragmentMoviesDetailsBinding
import com.alohagoha.aaaa.ui.rv.adapters.ActorsListAdapter
import com.alohagoha.aaaa.ui.rv.decorators.GridSpaceItemDecoration

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
            val spanCount = resources.getInteger(R.integer.actors_span_count)
            val spacing = resources.getDimension(R.dimen.padding_2x)
            layoutManager = GridLayoutManager(context, spanCount)
            addItemDecoration(GridSpaceItemDecoration(spacing, spacing, spanCount))
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _moviesDetailsBinding = null
    }
}
