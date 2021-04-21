package com.bangkit.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val actionbar = supportActionBar
        actionbar!!.title = "Details"
        actionbar.setDisplayHomeAsUpEnabled(true)

        val user = intent.getParcelableExtra(EXTRA_USER) as User
        val image = user.avatar

        Glide.with(this)
                .load(image)
                .into(img_user)

        tv_name.text = user.name.toString()
        tv_username.text = user.username.toString()
        tv_location.text = user.location.toString()
        tv_repository.text = user.repository.toString()
        tv_company.text = user.company.toString()
        tv_followers.text = user.followers.toString()
        tv_following.text = user.following.toString()

/*        val tvUsername: TextView = findViewById(R.id.tv_username)
        val tvName: TextView = findViewById(R.id.tv_name)
        val tvLocation: TextView = findViewById(R.id.tv_location)
        val tvRepository: TextView = findViewById(R.id.tv_repository)
        val tvCompany: TextView = findViewById(R.id.tv_company)
        val tvFollowers: TextView = findViewById(R.id.tv_followers)
        val tvFollowing: TextView = findViewById(R.id.tv_following)
        val imgPhoto: ImageView = findViewById(R.id.img_user)

        Glide.with(this)
                .load(image)
                .into(imgPhoto)

        tvUsername.text = user.username
        tvName.text = user.name
        tvLocation.text = user.location
        tvRepository.text = user.repository
        tvCompany.text = user.company
        tvFollowers.text = user.followers
        tvFollowing.text = user.following*/
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}