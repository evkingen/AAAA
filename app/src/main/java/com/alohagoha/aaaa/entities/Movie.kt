package com.alohagoha.aaaa.entities

import androidx.annotation.DrawableRes

data class Movie(
        val id: Long,
        val name: String,
        val genre: String,
        val restriction_rating: String,
        val rating: Float,
        val countReview: Int,
        val duration: Long,
        val isFavorite: Boolean = false,
        @DrawableRes val imageUrl: Int
)
