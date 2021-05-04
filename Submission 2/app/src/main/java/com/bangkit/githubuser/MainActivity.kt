package com.bangkit.githubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.githubuser.adapter.UserAdapter
import com.bangkit.githubuser.databinding.ActivityMainBinding
import com.bangkit.githubuser.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: UserAdapter
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar?.setIcon(R.mipmap.github_icon_round)

        shimmerView.visibility = View.GONE

        searchUser()
        showRecyclerList()
    }

    private fun showRecyclerList() {
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.adapter = adapter

        binding.searchText.visibility = View.VISIBLE

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        mainViewModel.getUsers().observe(this, { userItems ->
            if (userItems != null) {
                adapter.setData(userItems)
                showLoading(false)
                binding.rvUser.visibility = View.VISIBLE
            }
        })

        adapter.setOnItemClickCallback(object :
            UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showSelectedUser(data)
            }
        })
    }

    private fun searchUser() {
        binding.searchView.queryHint = resources.getString(R.string.search_user)
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty()) {
                    return true
                } else {
                    mainViewModel.setUser(query)
                }
                showLoading(true)
                binding.searchText.visibility = View.GONE
                binding.rvUser.visibility = View.GONE
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSelectedUser(user: User) {
        val moveIntent = Intent(this@MainActivity, DetailActivity::class.java)
        moveIntent.putExtra(DetailActivity.EXTRA_USER, user)
        startActivity(moveIntent)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            shimmerView.startShimmer()
            shimmerView.visibility = View.VISIBLE
        } else {
            shimmerView.stopShimmer()
            shimmerView.visibility = View.GONE
        }
    }
}