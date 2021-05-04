package com.bangkit.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.githubuser.R
import com.bangkit.githubuser.databinding.ItemUserBinding
import com.bangkit.githubuser.retrofit.Items
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class UserAdapter : RecyclerView.Adapter<UserAdapter.ListViewHolder>() {
    private val listUser = ArrayList<Items>()

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Items)
    }

    fun setData(items: ArrayList<Items>) {
        listUser.clear()
        listUser.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listUser[holder.adapterPosition])
        }
        holder.itemView.animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.main_animation)
    }

    override fun getItemCount(): Int = listUser.size

    inner class ListViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: Items) {
            with(binding) {
                Glide.with(itemView.context)
                        .load(user.avatar)
                        .apply(RequestOptions().override(500, 800))
                        .into(imgUser)
                tvUsername.text = user.username

            }
        }
    }
}