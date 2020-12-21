package com.alohagoha.aaaa.ui.rv.decorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alohagoha.aaaa.ui.rv.adapters.MoviesListAdapter
import kotlin.math.roundToInt

class GridSpaceItemDecoration(private val offsetX: Float = 0f,
                              private val offsetY: Float = 0f,
                              private val spanCount: Int = 1,
                              private val orientation: Int = GridLayoutManager.VERTICAL
) : RecyclerView.ItemDecoration() {
    private val bit
        get() = if (orientation == GridLayoutManager.VERTICAL)
            (offsetX / spanCount).roundToInt()
        else
            (offsetY / spanCount).roundToInt()


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        when (parent.adapter?.getItemViewType(parent.getChildAdapterPosition(view))) {
            MoviesListAdapter.HEADER_TYPE -> {
                outRect.bottom = offsetY.toInt()
            }
            else -> {
                outRect.top = offsetY.toInt()
            }
        }

        (view.layoutParams as? GridLayoutManager.LayoutParams)?.let { lp ->
            if (lp.spanSize != spanCount) {
                outRect.left = lp.spanIndex * bit
                outRect.right = (spanCount - lp.spanIndex - 1) * bit
            }
        }
    }
}
