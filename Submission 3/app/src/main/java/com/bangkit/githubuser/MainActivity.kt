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
import com.bangkit.githubuser.DetailActivity.Companion.EXTRA_USER
import com.bangkit.githubuser.adapter.UserAdapter
import com.bangkit.githubuser.databinding.ActivityMainBinding
import com.bangkit.githubuser.retrofit.Items
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

        supportActionBar?.setDisplayShowTitleEnabled(false)

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
        mainViewModel.getUser().observe(this, { userItems ->
            if (userItems != null) {
                adapter.setData(userItems)
                showLoading(false)
                binding.searchText.visibility = View.GONE
                binding.rvUser.visibility = View.VISIBLE
            }
        })

        adapter.setOnItemClickCallback(object :
            UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Items) {
                showSelectedUser(data)
            }
        })
    }

    private fun searchUser() {
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
        setMenu(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setMenu(selectedMenu: Int) {
        when (selectedMenu) {
            R.id.action_change_settings -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }

            R.id.action_favorite -> {
                val favIntent = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(favIntent)
            }

            R.id.action_notification_settings -> {
                val favIntent = Intent(this@MainActivity, NotificationSettingsActivity::class.java)
                startActivity(favIntent)
            }
        }
    }

    private fun showSelectedUser(user: Items) {
        val moveIntent = Intent(this@MainActivity, DetailActivity::class.java)
        moveIntent.putExtra(EXTRA_USER, user.username)
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