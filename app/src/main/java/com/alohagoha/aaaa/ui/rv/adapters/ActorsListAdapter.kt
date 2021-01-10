package com.alohagoha.aaaa.ui.rv.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alohagoha.aaaa.databinding.ViewHolderActorBinding
import com.alohagoha.aaaa.entities.Actor
import com.bumptech.glide.Glide

class ActorsListAdapter(private var actorList: List<Actor>) :
    RecyclerView.Adapter<ActorsListAdapter.ActorViewHolder>() {

    fun updateList(newActorList: List<Actor>) {
        actorList = newActorList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder =
        ActorViewHolder(
            ViewHolderActorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.bind(getActorItem(position))
    }

    private fun getActorItem(position: Int) = actorList[position]


    override fun getItemCount(): Int = actorList.size

    class ActorViewHolder(private val viewBinding: ViewHolderActorBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        init {
            viewBinding.actorImageIv.clipToOutline = true
        }

        fun bind(actor: Actor) {
            viewBinding.apply {
                actorNameTv.text = actor.name
                Glide.with(root.context).load(actor.picture)
                    .into(actorImageIv)
            }
        }
    }
}
