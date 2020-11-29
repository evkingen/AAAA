package com.alohagoha.aaaa.ui.utils.customview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Shader
import android.graphics.drawable.*
import android.graphics.drawable.shapes.RoundRectShape
import android.graphics.drawable.shapes.Shape
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatRatingBar
import com.alohagoha.aaaa.R

class ExtRatingBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.ratingBarStyle
) : AppCompatRatingBar(context, attrs, defStyleAttr) {
    var sampleTile: Bitmap? = null
    var innerSpacing: Int = 0


    private val drawableShape: Shape
        get() {
            val roundedCorners = floatArrayOf(5f, 5f, 5f, 5f, 5f, 5f, 5f, 5f)
            return RoundRectShape(roundedCorners, null, null)
        }

    init {
        attrs?.let {
            initAttrs(it)
        }
        val drawable = tileify(progressDrawable, false) as LayerDrawable

        progressDrawable = drawable
    }

    private fun initAttrs(attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ExtRatingBar)
        innerSpacing = a.getDimension(R.styleable.ExtRatingBar_innerSpacing, 0f).toInt()
        a.recycle()
    }

    private fun getBitmapDrawableFromVectorDrawable(drawable: Drawable): BitmapDrawable {
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth + innerSpacing, //dp between svg images  //* resources.displayMetrics.density
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)
        return BitmapDrawable(resources, bitmap)
    }


    private fun tileify(drawable: Drawable, clip: Boolean): Drawable {
        return when (drawable) {
            is DrawableWrapper -> {
                drawable.drawable?.let {
                    drawable.drawable = tileify(it, clip)
                }
                drawable
            }
            is LayerDrawable -> {
                val count = drawable.numberOfLayers
                val outDrawables = arrayOfNulls<Drawable>(count)
                for (i in 0 until count) {
                    val id = drawable.getId(i)
                    outDrawables[i] = tileify(
                        drawable.getDrawable(i),
                        id == android.R.id.progress || id == android.R.id.secondaryProgress
                    )
                }
                val newBg = LayerDrawable(outDrawables)
                for (i in 0 until count) {
                    newBg.setId(i, drawable.getId(i))
                }
                return newBg

            }
            is BitmapDrawable -> {
                val tileBitmap = drawable.bitmap
                if (sampleTile == null) {
                    sampleTile = tileBitmap
                }
                val shapeDrawable = ShapeDrawable(drawableShape)
                val bitmapShader = BitmapShader(
                    tileBitmap,
                    Shader.TileMode.REPEAT, Shader.TileMode.CLAMP
                )
                shapeDrawable.paint.shader = bitmapShader
                shapeDrawable.paint.colorFilter = drawable.paint.colorFilter
                return if (clip) ClipDrawable(
                    shapeDrawable, Gravity.START,
                    ClipDrawable.HORIZONTAL
                ) else shapeDrawable

            }
            else -> {
                tileify(getBitmapDrawableFromVectorDrawable(drawable), clip)
            }
        }

    }


    @Synchronized
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        sampleTile?.let {
            val width = it.width * numStars - innerSpacing + paddingStart + paddingEnd
            val height = it.height + paddingBottom + paddingTop
            setMeasuredDimension(
                resolveSizeAndState(width, widthMeasureSpec, 0),
                resolveSizeAndState(height, heightMeasureSpec, 0)
            )
        } ?: super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}