package com.bangkit.consumerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.consumerapp.adapter.FavoriteAdapter
import com.bangkit.consumerapp.databinding.ActivityFavoriteBinding
import com.bangkit.consumerapp.entity.Favorite
import com.bangkit.consumerapp.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var adapter: FavoriteAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onResume() {
        super.onResume()

        showRecyclerList()
    }

    private fun showRecyclerList() {
        adapter = FavoriteAdapter()
        adapter.notifyDataSetChanged()

        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.adapter = adapter

        favoriteViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FavoriteViewModel::class.java)
        favoriteViewModel.getQueryAll(this).observe(this, Observer {
            if (it.size > 0) {
                adapter.listFavorite = it
            } else {
                adapter.listFavorite = it
            }
        })

        adapter.setOnItemClickCallback(object :
            FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Favorite) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(favorite: Favorite) {
        val moveIntent = Intent(this@FavoriteActivity, DetailActivity::class.java)
        moveIntent.putExtra(DetailActivity.EXTRA_USER, favorite.username)
        startActivity(moveIntent)
    }
}