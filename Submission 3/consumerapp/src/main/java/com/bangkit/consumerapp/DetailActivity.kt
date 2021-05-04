package com.bangkit.consumerapp

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bangkit.consumerapp.databinding.ActivityDetailBinding
import com.bangkit.consumerapp.adapter.SectionPagerAdapter
import com.bangkit.consumerapp.database.DatabaseContract.FavColumns.Companion.AVATAR
import com.bangkit.consumerapp.database.DatabaseContract.FavColumns.Companion.CONTENT_URI
import com.bangkit.consumerapp.database.DatabaseContract.FavColumns.Companion.USERNAME
import com.bangkit.consumerapp.database.DatabaseContract.FavColumns.Companion.USER_ID
import com.bangkit.consumerapp.entity.Favorite
import com.bangkit.consumerapp.viewmodel.DetailViewModel
import com.bangkit.consumerapp.viewmodel.FavoriteViewModel
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.muddzdev.styleabletoast.StyleableToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
                R.string.following_text,
                R.string.followers_text
        )
    }

    private val TAG = DetailActivity::class.java.simpleName
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var binding: ActivityDetailBinding
    private lateinit var favorite: Favorite

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val username = intent.getStringExtra(EXTRA_USER)

        getViewModel(username)

        val sectionPagerAdapter = SectionPagerAdapter(this)
        sectionPagerAdapter.username = username
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun getViewModel(username: String?) {
        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
        detailViewModel.setDetailUser(username!!)
        detailViewModel.getDetailUser().observe(this, Observer {
            Log.d(TAG, "binding")
            binding.tvName.text = it?.name
            binding.tvUsername.text = username
            binding.tvLocation.text = it?.location
            binding.tvFollowers.text = it?.followers.toString()
            binding.tvFollowing.text = it?.following.toString()
            Glide.with(this)
                    .load(it?.avatar)
                    .into(binding.imgUser)
            if (it != null) {
                favorite = Favorite(
                        it.userId,
                        it.username,
                        it.avatar
                )
                checkFavButton(favorite)
            }
        })
    }

    private fun checkFavButton(favorite: Favorite) {
        val uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + favorite.userId)
        favoriteViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FavoriteViewModel::class.java)

        favoriteViewModel.getQueryById(this, uriWithId).observe(this, Observer {
            if (it.isNullOrEmpty()) {
                Log.d(TAG, "favButton")
                binding.fab.setImageResource(R.drawable.ic_favorite_unselected)
                favButton()
            } else {
                Log.d(TAG, "unfavButton")
                binding.fab.setImageResource(R.drawable.ic_favorite_selected)
                unFavButton(uriWithId)
            }
        })
    }

    private fun favButton() {
        binding.fab.setOnClickListener {

            GlobalScope.launch(Dispatchers.IO) {
                val values = ContentValues()
                values.put(USER_ID, favorite.userId)
                values.put(USERNAME, favorite.username)
                values.put(AVATAR, favorite.avatar)
                contentResolver.insert(CONTENT_URI, values)
            }

            binding.fab.setImageResource(R.drawable.ic_favorite_unselected)
            StyleableToast.makeText(this, "Added to Favorite", R.style.MyToastTrue).show()
            checkFavButton(favorite)
        }

    }

    private fun unFavButton(uriWithId: Uri) {
        binding.fab.setOnClickListener {

            GlobalScope.launch(Dispatchers.IO) {
                contentResolver.delete(uriWithId, null, null)
            }
            binding.fab.setImageResource(R.drawable.ic_favorite_selected)
            StyleableToast.makeText(this, "Removed from Favorite", R.style.MyToastFalse).show()
            checkFavButton(favorite)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}