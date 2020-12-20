package com.alohagoha.aaaa.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alohagoha.aaaa.databinding.ActorItemBinding
import com.alohagoha.aaaa.entities.Actor
import com.bumptech.glide.Glide

class ActorsListAdapter(private val actorList: List<Actor>) :
    RecyclerView.Adapter<ActorsListAdapter.ActorViewHolder>() {
    class ActorViewHolder(private val viewBinding: ActorItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        init {
            viewBinding.actorImageIv.clipToOutline = true
        }

        fun bind(actor: Actor) {
            viewBinding.actorNameTv.text = actor.name
            Glide.with(viewBinding.root.context).load(actor.imageResId)
                .into(viewBinding.actorImageIv)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val actorBinding =
            ActorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActorViewHolder(actorBinding)
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.bind(actorList[position])
    }

    override fun getItemCount(): Int = actorList.size

}
