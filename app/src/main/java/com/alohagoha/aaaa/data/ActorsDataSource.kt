package com.alohagoha.aaaa.data

import com.alohagoha.aaaa.R
import com.alohagoha.aaaa.entities.Actor

object ActorsDataSource : IDataSource<Actor> {

    private val actorList = listOf(
        Actor(1, "Robert Downey Jr.", R.drawable.actor1),
        Actor(2, "Chris Evans", R.drawable.actor2),
        Actor(3, "Mark Ruffalo", R.drawable.actor3),
        Actor(4, "Chris Hemsworth", R.drawable.actor4)
    )

    override fun getAllData(): List<Actor> {
        return actorList
    }
}