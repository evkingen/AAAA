package com.alohagoha.aaaa.ui.rv.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alohagoha.aaaa.databinding.ViewHolderActorBinding
import com.alohagoha.aaaa.entities.Actor
import com.bumptech.glide.Glide

class ActorsListAdapter(private val actorList: List<Actor>) :
        RecyclerView.Adapter<ActorsListAdapter.ActorViewHolder>() {

    class ActorViewHolder(private val viewBinding: ViewHolderActorBinding) :
            RecyclerView.ViewHolder(viewBinding.root) {
        init {
            viewBinding.actorImageIv.clipToOutline = true
        }

        fun bind(actor: Actor) {
            viewBinding.apply {
                actorNameTv.text = actor.name
                Glide.with(root.context).load(actor.imageResId)
                        .into(actorImageIv)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder =
            ActorViewHolder(ViewHolderActorBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.bind(getItemView(position))
    }

    private fun getItemView(position: Int) = actorList[position]


    override fun getItemCount(): Int = actorList.size

}
