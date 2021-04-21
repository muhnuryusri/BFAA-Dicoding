package com.bangkit.githubuser

import android.content.Intent
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var rvUser: RecyclerView

    private lateinit var dataUsername: Array<String>
    private lateinit var dataName: Array<String>
    private lateinit var dataLocation: Array<String>
    private lateinit var dataRepository: Array<String>
    private lateinit var dataCompany: Array<String>
    private lateinit var dataFollowers: Array<String>
    private lateinit var dataFollowing: Array<String>
    private lateinit var dataAvatar: TypedArray
    private var users = arrayListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.apply {
            title = "GitHub User"
            setDisplayShowHomeEnabled(true)
            setDisplayUseLogoEnabled(true)
            setLogo(R.drawable.actionbar_logo)
        }
        rvUser = findViewById(R.id.rv_user)
        rvUser.setHasFixedSize(true)

        showRecyclerList()
        prepare()
        addItem()
    }

    private fun showRecyclerList() {
        rvUser.layoutManager = LinearLayoutManager(this)
        val userAdapter = UserAdapter(users)
        rvUser.adapter = userAdapter

        userAdapter.setOnItemClickCallback(object :
                UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showSelectedUser(data)
            }
        })
    }

    private fun prepare() {
        dataUsername = resources.getStringArray(R.array.username)
        dataName = resources.getStringArray(R.array.name)
        dataLocation = resources.getStringArray(R.array.location)
        dataRepository = resources.getStringArray(R.array.repository)
        dataCompany = resources.getStringArray(R.array.company)
        dataFollowers = resources.getStringArray(R.array.followers)
        dataFollowing = resources.getStringArray(R.array.following)
        dataAvatar = resources.obtainTypedArray(R.array.avatar)
    }

    private fun addItem(): ArrayList<User> {
        for (position in dataName.indices) {
            val user = User(
                    dataUsername[position],
                    dataName[position],
                    dataLocation[position],
                    dataRepository[position],
                    dataCompany[position],
                    dataFollowers[position],
                    dataFollowing[position],
                    dataAvatar.getResourceId(position, -1)
            )
            users.add(user)
        }
        dataAvatar.recycle()
        return users
    }

    private fun showSelectedUser(user: User) {
        val moveIntent = Intent(this@MainActivity, DetailActivity::class.java)
        moveIntent.putExtra(DetailActivity.EXTRA_USER, user)
        startActivity(moveIntent)
    }
}