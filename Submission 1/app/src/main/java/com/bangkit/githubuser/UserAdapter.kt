package com.bangkit.githubuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class UserAdapter (private val listUser: ArrayList<User>): RecyclerView.Adapter<UserAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]
        Glide.with(holder.itemView.context)
                .load(user.avatar)
                .apply(RequestOptions().override(500, 800))
                .into(holder.imgPhoto)
        holder.tvUsername.text = user.username
        holder.tvName.text = user.name
        holder.tvFollowers.text = user.followers
        holder.tvFollowing.text = user.following
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listUser[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvUsername: TextView = itemView.findViewById(R.id.tv_username)
        var tvName: TextView = itemView.findViewById(R.id.tv_name)
        var tvFollowers: TextView = itemView.findViewById(R.id.tv_followers)
        var tvFollowing: TextView = itemView.findViewById(R.id.tv_following)
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_user)
    }
}