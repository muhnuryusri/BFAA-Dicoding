package com.bangkit.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.githubuser.User
import com.bangkit.githubuser.databinding.ItemUserBinding
import com.bangkit.githubuser.entity.Favorite
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    var listFavorite = ArrayList<Favorite>()
        set(favorite) {
            this.listFavorite.clear()
            this.listFavorite.addAll(favorite)
            notifyDataSetChanged()
        }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Favorite)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): FavoriteViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorite[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listFavorite[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listFavorite.size

    inner class FavoriteViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite) {
            with(binding) {
                Glide.with(itemView.context)
                        .load(favorite.avatar)
                        .apply(RequestOptions().override(500, 800))
                        .into(imgUser)
                tvUsername.text = favorite.username

            }
        }
    }
}