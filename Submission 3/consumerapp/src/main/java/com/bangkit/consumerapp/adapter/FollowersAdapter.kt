package com.bangkit.consumerapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.consumerapp.R
import com.bangkit.consumerapp.databinding.ItemUserBinding
import com.bangkit.consumerapp.retrofit.FollowersResponse
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class FollowersAdapter : RecyclerView.Adapter<FollowersAdapter.ViewHolder>() {

    private val listFollowersUser = ArrayList<FollowersResponse>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: FollowersResponse)
    }

    fun setFollowersData(items: ArrayList<FollowersResponse>) {
        listFollowersUser.clear()
        listFollowersUser.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listFollowersUser.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listFollowersUser[position])
        holder.itemView.animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.main_animation)
    }

    inner class ViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: FollowersResponse) {
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